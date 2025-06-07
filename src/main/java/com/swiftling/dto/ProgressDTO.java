package com.swiftling.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgressDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer learned;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer added;

}
