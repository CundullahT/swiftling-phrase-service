package com.swiftling.service;

import com.swiftling.dto.PhraseDTO;
import com.swiftling.enums.Language;

import java.util.List;

public interface PhraseService {

    PhraseDTO create(PhraseDTO phraseDTO);

    List<Language> getLanguages();

}
