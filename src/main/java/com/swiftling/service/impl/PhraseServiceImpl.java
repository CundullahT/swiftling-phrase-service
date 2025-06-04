package com.swiftling.service.impl;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import com.swiftling.client.UserAccountClient;
import com.swiftling.dto.PhraseDTO;
import com.swiftling.dto.UserAccountResponseDTO;
import com.swiftling.entity.Phrase;
import com.swiftling.entity.PhraseTag;
import com.swiftling.entity.Tag;
import com.swiftling.enums.DefaultTag;
import com.swiftling.enums.Language;
import com.swiftling.enums.Status;
import com.swiftling.exception.ExternalIdNotRetrievedException;
import com.swiftling.exception.PhraseAlreadyExistsException;
import com.swiftling.exception.PhraseCanNotBeDeletedException;
import com.swiftling.exception.PhraseNotFoundException;
import com.swiftling.repository.PhraseRepository;
import com.swiftling.repository.TagRepository;
import com.swiftling.service.PhraseService;
import com.swiftling.util.MapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
public class PhraseServiceImpl implements PhraseService {

    private final PhraseRepository phraseRepository;
    private final MapperUtil mapperUtil;
    private final UserAccountClient userAccountClient;
    private final TagRepository tagRepository;

    public PhraseServiceImpl(PhraseRepository phraseRepository, MapperUtil mapperUtil, UserAccountClient userAccountClient, TagRepository tagRepository) {
        this.phraseRepository = phraseRepository;
        this.mapperUtil = mapperUtil;
        this.userAccountClient = userAccountClient;
        this.tagRepository = tagRepository;
    }

    @Override
    public PhraseDTO create(PhraseDTO phraseDTO) {

        phraseRepository
                .findByOriginalPhraseAndOwnerUserAccountId(phraseDTO.getOriginalPhrase(), getOwnerUserAccountId())
                .ifPresent(existingPhrase -> {
                    throw new PhraseAlreadyExistsException("The given phrase already exists: " + existingPhrase.getOriginalPhrase());
                });

        Phrase phraseToSave = mapperUtil.convert(phraseDTO, new Phrase());

        phraseToSave.setExternalPhraseId(UUID.randomUUID());
        phraseToSave.setConsecutiveCorrectAnswerAmount(0);
        phraseToSave.setOriginalLanguage(Language.findByValue(phraseDTO.getOriginalLanguage()));
        phraseToSave.setMeaningLanguage(Language.findByValue(phraseDTO.getMeaningLanguage()));
        phraseToSave.setStatus(Status.IN_PROGRESS);
        phraseToSave.setOwnerUserAccountId(getOwnerUserAccountId());
        phraseToSave.setInsertDateTime(LocalDateTime.now());

        setPhraseTags(phraseToSave, phraseDTO);

        Phrase savedPhrase = phraseRepository.save(phraseToSave);

        PhraseDTO phraseToReturn = mapperUtil.convert(savedPhrase, new PhraseDTO());

        setPhraseDTOTags(savedPhrase, phraseToReturn);

        return phraseToReturn;

    }

    @Override
    public List<PhraseDTO> getPhrases(String status, String language) {

        return phraseRepository.findAllByOwnerUserAccountIdAndStatusAndLanguage(getOwnerUserAccountId(), status, language)
                .stream().map(phrase -> {

                    PhraseDTO phraseDTO = mapperUtil.convert(phrase, new PhraseDTO());

                    phraseDTO.setOriginalLanguage(phrase.getOriginalLanguage().getValue());
                    phraseDTO.setMeaningLanguage(phrase.getMeaningLanguage().getValue());
                    phraseDTO.setStatus(phrase.getStatus().getValue());

                    setPhraseDTOTags(phrase, phraseDTO);

                    return phraseDTO;

                }).toList();

    }

    @Override
    public List<PhraseDTO> getLastTenPhrases() {

        return phraseRepository.findTop10ByOwnerUserAccountIdOrderByInsertDateTimeDesc(getOwnerUserAccountId())
                .stream().map(phrase -> {

                    PhraseDTO phraseDTO = mapperUtil.convert(phrase, new PhraseDTO());

                    phraseDTO.setOriginalLanguage(phrase.getOriginalLanguage().getValue());
                    phraseDTO.setMeaningLanguage(phrase.getMeaningLanguage().getValue());
                    phraseDTO.setStatus(phrase.getStatus().getValue());

                    setPhraseDTOTags(phrase, phraseDTO);

                    return phraseDTO;

                }).toList();

    }

    @Override
    public PhraseDTO getPhraseDetails(UUID externalPhraseId) {

        Phrase phrase = phraseRepository.findByExternalPhraseIdAndOwnerUserAccountId(externalPhraseId, getOwnerUserAccountId())
                .orElseThrow(() -> new PhraseNotFoundException("The phrase does not exist: " + externalPhraseId));

        PhraseDTO phraseDTO = mapperUtil.convert(phrase, new PhraseDTO());

        phraseDTO.setOriginalLanguage(phrase.getOriginalLanguage().getValue());
        phraseDTO.setMeaningLanguage(phrase.getMeaningLanguage().getValue());
        phraseDTO.setStatus(phrase.getStatus().getValue());

        setPhraseDTOTags(phrase, phraseDTO);

        return phraseDTO;

    }

    @Override
    public List<String> getLanguages() {
        return Stream.of(Language.values())
                .map(Language::getValue).toList();
    }

    @Override
    public List<String> getQuizLanguages() {
        return phraseRepository.findAllDistinctLanguages();
    }

    @Override
    public List<String> getTags() {

        List<String> allTags = new ArrayList<>();

        List<String> defaultTags = Stream.of(DefaultTag.values())
                .map(DefaultTag::getValue).toList();

        List<String> savedTags = tagRepository.findAllByPhraseOwner(getOwnerUserAccountId())
                .stream().map(Tag::getTagName)
                .toList();

        allTags.addAll(defaultTags);
        allTags.addAll(savedTags);

        return allTags;

    }

    @Override
    public PhraseDTO update(UUID externalPhraseId, PhraseDTO phraseDTO) {

        Phrase foundPhrase = phraseRepository.findByExternalPhraseIdAndOwnerUserAccountId(externalPhraseId, getOwnerUserAccountId())
                .orElseThrow(() -> new PhraseNotFoundException("The phrase does not exist: " + externalPhraseId));

        Phrase phraseToUpdate = mapperUtil.convert(phraseDTO, new Phrase());

        phraseToUpdate.setExternalPhraseId(foundPhrase.getExternalPhraseId());
        phraseToUpdate.setConsecutiveCorrectAnswerAmount(0);
        phraseToUpdate.setOriginalLanguage(Language.findByValue(phraseDTO.getOriginalLanguage()));
        phraseToUpdate.setMeaningLanguage(Language.findByValue(phraseDTO.getMeaningLanguage()));
        phraseToUpdate.setStatus(Status.IN_PROGRESS);
        phraseToUpdate.setOwnerUserAccountId(foundPhrase.getOwnerUserAccountId());
        phraseToUpdate.setInsertDateTime(LocalDateTime.now());

        setPhraseTags(phraseToUpdate, phraseDTO);

        Phrase updatedPhrase = phraseRepository.save(phraseToUpdate);

        PhraseDTO phraseToReturn = mapperUtil.convert(updatedPhrase, new PhraseDTO());

        setPhraseDTOTags(updatedPhrase, phraseToReturn);

        return phraseToReturn;

    }

    @Override
    public void delete(UUID externalPhraseId) {

        Phrase phraseToDelete = phraseRepository.findByExternalPhraseIdAndOwnerUserAccountId(externalPhraseId, getOwnerUserAccountId())
                .orElseThrow(() -> new PhraseNotFoundException("The phrase does not exist: " + externalPhraseId));

        try {
            phraseRepository.delete(phraseToDelete);
        } catch (Throwable exception) {
            throw new PhraseCanNotBeDeletedException("The phrase can not be deleted: " + externalPhraseId);
        }

    }

    @Override
    public void originalPhraseSynthesizeSpeech(UUID externalPhraseId, String outputFileName) throws IOException {

        Phrase foundPhrase = phraseRepository.findByExternalPhraseIdAndOwnerUserAccountId(externalPhraseId, getOwnerUserAccountId())
                .orElseThrow(() -> new PhraseNotFoundException("The phrase does not exist: " + externalPhraseId));

        String languageCode = foundPhrase.getOriginalLanguage().getCode();

        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {

            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(foundPhrase.getOriginalPhrase())
                    .build();

            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode(languageCode)
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                    .build();

            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
            ByteString audioContents = response.getAudioContent();

            try (OutputStream out = new FileOutputStream(outputFileName)) {
                out.write(audioContents.toByteArray());
                System.out.println("Audio content written to file: " + outputFileName);
            }

        }

    }

    @Override
    public void meaningPhraseSynthesizeSpeech(UUID externalPhraseId, String outputFileName) throws IOException {

        Phrase foundPhrase = phraseRepository.findByExternalPhraseIdAndOwnerUserAccountId(externalPhraseId, getOwnerUserAccountId())
                .orElseThrow(() -> new PhraseNotFoundException("The phrase does not exist: " + externalPhraseId));

        String languageCode = foundPhrase.getMeaningLanguage().getCode();

        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {

            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(foundPhrase.getMeaning())
                    .build();

            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode(languageCode)
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                    .build();

            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
            ByteString audioContents = response.getAudioContent();

            try (OutputStream out = new FileOutputStream(outputFileName)) {
                out.write(audioContents.toByteArray());
                System.out.println("Audio content written to file: " + outputFileName);
            }

        }

    }

    private UUID getOwnerUserAccountId() {

        UUID ownerUserAccountId;

        ResponseEntity<UserAccountResponseDTO> response = userAccountClient.getUserAccountExternalId();

        if(Objects.requireNonNull(response.getBody()).isSuccess() && Objects.requireNonNull(response.getBody()).getData() != null) {
            ownerUserAccountId = (UUID) response.getBody().getData();
        } else {
            throw new ExternalIdNotRetrievedException("The external ID of the user account could not be retrieved.");
        }

        return ownerUserAccountId;

    }

    private void setPhraseTags(Phrase phrase, PhraseDTO phraseDTO) {

        phrase.getPhraseTags().clear();

        for (String tagName : phraseDTO.getPhraseTags()) {
            Tag tag = tagRepository.findByOwnerUserAccountIdAndTagName(phrase.getOwnerUserAccountId(), tagName)
                    .orElseGet(() -> {
                        Tag newTag = new Tag();
                        newTag.setTagName(tagName);
                        return tagRepository.save(newTag);
                    });
            phrase.addTag(tag);
        }

    }

    private void setPhraseDTOTags(Phrase phrase, PhraseDTO phraseDTO) {

        phraseDTO.getPhraseTags().clear();

        for (PhraseTag phraseTag : phrase.getPhraseTags()) {
            phraseDTO.getPhraseTags().add(phraseTag.getTag().getTagName());
        }

    }

}
