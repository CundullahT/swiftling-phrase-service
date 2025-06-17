package com.swiftling.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(columnNames = {"owner_user_account_id", "tag_name"}))
public class Tag extends BaseEntity {

    @Column(nullable = false)
    private String tagName;

    @Column(name = "owner_user_account_id", nullable = false)
    private UUID ownerUserAccountId;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhraseTag> phraseTags = new ArrayList<>();

}
