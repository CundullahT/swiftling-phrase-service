package com.swiftling.service.impl;

import com.swiftling.client.UserAccountClient;
import com.swiftling.dto.UserAccountResponseDTO;
import com.swiftling.dto.UserProgressMessageDTO;
import com.swiftling.exception.ExternalIdNotRetrievedException;
import com.swiftling.service.KafkaProducerService;
import com.swiftling.service.PhraseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@EnableScheduling
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PhraseService phraseService;
    private final UserAccountClient userAccountClient;

    @Value("${kafka.topic.user-progress}")
    private String userProgressTopic;

    public KafkaProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate, PhraseService phraseService, UserAccountClient userAccountClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.phraseService = phraseService;
        this.userAccountClient = userAccountClient;
    }

    @Override
    public void sendUserProgressMessage(UserProgressMessageDTO userProgressMessageDTO) {
        log.info("Sending user progress message to Kafka: {}", userProgressMessageDTO);
        kafkaTemplate.send(userProgressTopic, userProgressMessageDTO);
    }

    /**
     * Scheduled task to send user progress messages daily at 9:00 AM.
     * The cron expression "0 0 9 * * ?" means:
     * - 0 seconds
     * - 0 minutes
     * - 9 hours
     * - every day of the month
     * - every month
     * - every day of the week
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendUserProgressMessage() {

        log.info("Executing scheduled task to send daily user progress message at 9 AM");

        try {

            // Get the user account ID
            UUID userAccountId = getUserAccountId();

            // Get the user's progress
            var progress = phraseService.getProgress();

            // Create the message DTO
            UserProgressMessageDTO messageDTO = UserProgressMessageDTO.builder()
                    .userAccountId(userAccountId)
                    .progress(progress)
                    .timestamp(LocalDateTime.now())
                    .build();

            // Send the message
            sendUserProgressMessage(messageDTO);

            log.info("Successfully sent user progress message");

        } catch (Exception e) {
            log.error("Error sending user progress message", e);
        }
    }

    private UUID getUserAccountId() {

        try {

            UUID ownerUserAccountId;

            ResponseEntity<UserAccountResponseDTO> response = userAccountClient.getUserAccountExternalId();

            if (Objects.requireNonNull(response.getBody()).isSuccess() && Objects.requireNonNull(response.getBody()).getData() != null) {
                ownerUserAccountId = (UUID) response.getBody().getData();
            } else {
                throw new ExternalIdNotRetrievedException("The external ID of the user account could not be retrieved.");
            }

            return ownerUserAccountId;

        } catch (Throwable exception) {
            log.error("Error getting user account ID: {}", exception.getMessage(), exception);
            throw new ExternalIdNotRetrievedException("The external ID of the user account could not be retrieved.");
        }

    }

}
