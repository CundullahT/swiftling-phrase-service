package com.swiftling.service.impl;

import com.swiftling.client.UserAccountClient;
import com.swiftling.dto.PhraseDTO;
import com.swiftling.dto.UserAccountResponseDTO;
import com.swiftling.entity.Phrase;
import com.swiftling.entity.Tag;
import com.swiftling.exception.ExternalIdNotRetrievedException;
import com.swiftling.exception.PhraseAlreadyExistsException;
import com.swiftling.repository.PhraseRepository;
import com.swiftling.repository.TagRepository;
import com.swiftling.service.KeycloakService;
import com.swiftling.service.PhraseService;
import com.swiftling.util.MapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhraseServiceImpl implements PhraseService {

    private final PhraseRepository phraseRepository;
    private final MapperUtil mapperUtil;
    private final UserAccountClient userAccountClient;
    private final KeycloakService keycloakService;
    private final TagRepository tagRepository;

    public PhraseServiceImpl(PhraseRepository phraseRepository, MapperUtil mapperUtil, UserAccountClient userAccountClient, KeycloakService keycloakService, TagRepository tagRepository) {
        this.phraseRepository = phraseRepository;
        this.mapperUtil = mapperUtil;
        this.userAccountClient = userAccountClient;
        this.keycloakService = keycloakService;
        this.tagRepository = tagRepository;
    }

    @Override
    public PhraseDTO create(PhraseDTO phraseDTO) {

        phraseRepository
                .findByOriginalPhraseAndOwnerUserAccountId(phraseDTO.getOriginalPhrase(), phraseDTO.getOwnerUserAccountId())
                .ifPresent(existingPhrase -> {
                    throw new PhraseAlreadyExistsException("The given phrase already exists: " + existingPhrase.getOriginalPhrase());
                });

        Phrase phraseToSave = mapperUtil.convert(phraseDTO, new Phrase());

        phraseToSave.setExternalPhraseId(UUID.randomUUID());
        phraseToSave.setConsecutiveCorrectAnswerAmount(0);
        phraseToSave.setOwnerUserAccountId(getOwnerUserAccountId());

        setPhraseTags(phraseToSave, phraseDTO);

        Phrase savedPhrase = phraseRepository.save(phraseToSave);

        return mapperUtil.convert(savedPhrase, new PhraseDTO());

    }

    private UUID getOwnerUserAccountId() {

        UUID ownerUserAccountId;

        String email = keycloakService.getLoggedInUserName();

        ResponseEntity<UserAccountResponseDTO> response = userAccountClient.getNonCompletedCountByAssignedManager(email);

        if(Objects.requireNonNull(response.getBody()).isSuccess() && Objects.requireNonNull(response.getBody()).getData() != null) {
            ownerUserAccountId = (UUID) response.getBody().getData();
        } else {
            throw new ExternalIdNotRetrievedException("External ID of the user account can not be retrieved.");
        }

        return ownerUserAccountId;

    }

    private void setPhraseTags(Phrase phrase, PhraseDTO phraseDTO) {

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

}
