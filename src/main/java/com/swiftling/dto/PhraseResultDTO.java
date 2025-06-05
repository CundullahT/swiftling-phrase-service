package com.swiftling.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhraseResultDTO {

    @NotNull(message = "Consecutive Correct Amount is a required field.")
    private Integer consecutiveCorrectAmount;

    @NotNull(message = "Answered Wrong Or Timed Out At Least Once is a required field.")
    private Boolean answeredWrongOrTimedOutAtLeastOnce;

}
