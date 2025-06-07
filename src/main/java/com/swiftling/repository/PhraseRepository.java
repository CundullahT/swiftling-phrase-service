package com.swiftling.repository;

import com.swiftling.entity.Phrase;
import com.swiftling.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    Optional<Phrase> findByOriginalPhraseAndOwnerUserAccountId(String originalPhrase, UUID ownerUserAccountId);

    Optional<Phrase> findByExternalPhraseIdAndOwnerUserAccountId(UUID externalPhraseId, UUID ownerUserAccountId);

    @Query(value = """
            SELECT * FROM phrases
             WHERE owner_user_account_id = :ownerUserAccountId
             AND (:status IS NULL OR status = :status)
             AND (
                   :language IS NULL OR original_language = :language OR meaning_language = :language
             )
            """, nativeQuery = true)
    List<Phrase> findAllByOwnerUserAccountIdAndOrStatusAndOrLanguage(@Param("ownerUserAccountId") UUID ownerUserAccountId,
                                                                 @Param("status") String status,
                                                                 @Param("language") String language);

    List<Phrase> findTop10ByOwnerUserAccountIdOrderByInsertDateTimeDesc(UUID ownerUserAccountId);
    
    @Query(value = """
            SELECT DISTINCT language FROM (
             SELECT original_language AS language FROM phrases
             UNION
             SELECT meaning_language AS language FROM phrases
            ) AS combined_languages
            """, nativeQuery = true)
    List<String> findAllDistinctLanguages();

    Integer countAllByOwnerUserAccountIdAndStatus(UUID ownerUserAccountId, Status status);

    Integer countAllByOwnerUserAccountId(UUID ownerUserAccountId);

    Integer countAllByOwnerUserAccountIdAndStatusAndInsertDateTimeAfter(UUID ownerUserAccountId, Status status, LocalDateTime insertDateTime);

    Integer countAllByOwnerUserAccountIdAndInsertDateTimeAfter(UUID ownerUserAccountId, LocalDateTime insertDateTime);

}
