package com.swiftling.service;

import com.swiftling.dto.PhraseDTO;

import java.util.List;

public interface PhraseService {

    PhraseDTO create(PhraseDTO phraseDTO);

    List<String> getLanguages();

    List<String> getTags();

}
