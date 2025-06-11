package com.swiftling.service;

import com.swiftling.dto.PhraseDTO;
import com.swiftling.dto.PhraseResultDTO;
import com.swiftling.dto.ProgressDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PhraseService {

    PhraseDTO create(PhraseDTO phraseDTO);

    List<PhraseDTO> getPhrases(String status, String language);

    List<PhraseDTO> getLastTenPhrases();

    PhraseDTO getPhraseDetails(UUID externalPhraseId);

    List<String> getLanguages();

    List<String> getQuizLanguages();

    List<String> getTags();

    PhraseDTO update(UUID externalPhraseId, PhraseDTO phraseDTO);

    void updateStatuses(Map<UUID, PhraseResultDTO> resultForEachPhrase);

    void delete(UUID externalPhraseId);

    void originalPhraseSynthesizeSpeech(UUID externalPhraseId, String outputFileName) throws Exception;

    void meaningPhraseSynthesizeSpeech(UUID externalPhraseId, String outputFileName) throws Exception;

    Map<String, ProgressDTO> getProgress();

    Map<UUID, Map<String, ProgressDTO>> getAllUsersProgress();

}
