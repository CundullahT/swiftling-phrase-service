package com.swiftling.service;

import com.swiftling.dto.UserProgressMessageDTO;

public interface KafkaProducerService {

    void sendUserProgressMessage(UserProgressMessageDTO userProgressMessageDTO);
}