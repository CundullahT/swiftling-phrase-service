package com.swiftling.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "phrase_tags", uniqueConstraints = @UniqueConstraint(columnNames = {"phrase_id", "tag_id"}))
public class PhraseTag extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "phrase_id", nullable = false)
    private Phrase phrase;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public PhraseTag(Phrase phrase, Tag tag) {
        this.phrase = phrase;
        this.tag = tag;
    }

}
