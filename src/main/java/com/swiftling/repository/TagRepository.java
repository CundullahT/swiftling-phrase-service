package com.swiftling.repository;

import com.swiftling.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagNameEqualsIgnoreCase(String tagName);

    @Query("""
            SELECT DISTINCT t
              FROM Tag t
              JOIN t.phraseTags pt
              JOIN pt.phrase p
             WHERE p.ownerUserAccountId = :ownerId
             AND LOWER(t.tagName) = LOWER(:tagName)
            """)
    Optional<Tag> findByOwnerUserAccountIdAndTagName(@Param("ownerId") UUID ownerId, @Param("tagName") String tagName);


    @Query("""
        SELECT DISTINCT t
          FROM Tag t
          JOIN t.phraseTags pt
          JOIN pt.phrase p
         WHERE p.ownerUserAccountId = :ownerId
        """)
    List<Tag> findAllByPhraseOwner(@Param("ownerId") UUID ownerId);

}
