package com.swiftling.service;

import com.swiftling.dto.PhraseDTO;

import java.util.List;

public interface PhraseService {

    PhraseDTO create(PhraseDTO phraseDTO);

    List<PhraseDTO> getPhrases(String status, String language);

    List<String> getLanguages();

    List<String> getQuizLanguages();

    List<String> getTags();

}
