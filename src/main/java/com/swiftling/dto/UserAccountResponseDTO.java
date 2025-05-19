package com.swiftling.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccountResponseDTO {

    private boolean success;
    private HttpStatus statusCode;
    private String message;
    private Object data;

}
