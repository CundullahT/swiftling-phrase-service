package com.swiftling.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swiftling.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhraseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID externalPhraseId;

    @NotBlank(message = "Original Phrase is a required field.")
    private String originalPhrase;

    @NotNull(message = "Original Language is a required field.")
    private Language originalLanguage;

    @NotBlank(message = "Meaning is a required field.")
    private String meaning;

    @NotNull(message = "Meaning Language is a required field.")
    private Language meaningLanguage;

    private List<String> phraseTags;

    private String notes;

    @Setter(AccessLevel.NONE)
    private Integer consecutiveCorrectAnswerAmount;

    private UUID ownerUserAccountId;

}
