package com.swiftling.service;

import com.swiftling.dto.PhraseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface PhraseService {

    PhraseDTO create(PhraseDTO phraseDTO);

    List<PhraseDTO> getPhrases(String status, String language);

    List<PhraseDTO> getLastTenPhrases();

    PhraseDTO getPhraseDetails(UUID externalPhraseId);

    List<String> getLanguages();

    List<String> getQuizLanguages();

    List<String> getTags();

    PhraseDTO update(UUID externalPhraseId, @Valid PhraseDTO phraseDTO);

}
