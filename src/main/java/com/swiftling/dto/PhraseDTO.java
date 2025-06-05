package com.swiftling.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Original Language is a required field.")
    private String originalLanguage;

    @NotBlank(message = "Meaning is a required field.")
    private String meaning;

    @NotBlank(message = "Meaning Language is a required field.")
    private String meaningLanguage;

    private List<String> phraseTags;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;

    private String notes;

//    @Setter(AccessLevel.NONE)
//    private Integer consecutiveCorrectAnswerAmount;

}
