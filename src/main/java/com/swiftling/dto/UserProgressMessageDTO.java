package com.swiftling.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProgressMessageDTO {

    private UUID userAccountId;
    private Map<String, ProgressDTO> progress;
    private LocalDateTime timestamp;

}
