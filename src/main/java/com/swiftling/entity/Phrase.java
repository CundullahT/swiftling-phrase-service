package com.swiftling.entity;

import com.swiftling.enums.Language;
import com.swiftling.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "phrases")
public class Phrase extends BaseEntity {

    @Column(unique = true, nullable = false)
    private UUID externalPhraseId;

    @Column(nullable = false)
    private String originalPhrase;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language originalLanguage;

    @Column(nullable = false)
    private String meaning;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language meaningLanguage;

    @OneToMany(mappedBy = "phrase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhraseTag> phraseTags = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    private String notes;

    @Column(nullable = false)
    private Integer consecutiveCorrectAnswerAmount;

    @Column(nullable = false, updatable = false)
    private UUID ownerUserAccountId;

    @Column(nullable = false)
    private LocalDateTime insertDateTime;

    public void addTag(Tag tag) {
        if (phraseTags.stream().noneMatch(pt -> pt.getTag().equals(tag))) {
            PhraseTag pt = new PhraseTag(this, tag);
            phraseTags.add(pt);
        }
    }

    public void removeTag(Tag tag) {
        phraseTags.removeIf(pt -> pt.getTag().equals(tag));
        tag.getPhraseTags().removeIf(pt -> pt.getPhrase().equals(this));
    }

}
