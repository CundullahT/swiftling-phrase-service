package com.swiftling.service.impl;

import com.swiftling.dto.ProgressDTO;
import com.swiftling.dto.UserProgressMessageDTO;
import com.swiftling.service.KafkaProducerService;
import com.swiftling.service.PhraseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@EnableScheduling
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final PhraseService phraseService;

    @Value("${kafka.topic.user-progress}")
    private String userProgressTopic;

    public KafkaProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate, PhraseService phraseService) {
        this.kafkaTemplate = kafkaTemplate;
        this.phraseService = phraseService;
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
    public void sendAllUsersProgressMessages() {

        log.info("Executing scheduled task to send daily progress messages for all users at 9 AM");

        try {

            Map<UUID, Map<String, ProgressDTO>> allUserProgress = phraseService.getAllUsersProgress();

            allUserProgress.forEach((userId, progressMap) -> {
                UserProgressMessageDTO message = UserProgressMessageDTO.builder()
                        .userAccountId(userId)
                        .progress(progressMap)
                        .timestamp(LocalDateTime.now())
                        .build();

                sendUserProgressMessage(message);

            });

            log.info("Successfully sent progress messages for {} users", allUserProgress.size());

        } catch (Exception e) {
            log.error("Error sending progress messages to Kafka", e);
        }

    }

}
