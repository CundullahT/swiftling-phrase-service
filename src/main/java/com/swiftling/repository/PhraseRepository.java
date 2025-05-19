package com.swiftling.repository;

import com.swiftling.entity.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    Optional<Phrase> findByOriginalPhraseAndOwnerUserAccountId(String originalPhrase, UUID ownerUserAccountId);

}
