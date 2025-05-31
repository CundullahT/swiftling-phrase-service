package com.swiftling.client;

import com.swiftling.dto.UserAccountResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "swiftling-user-service")
public interface UserAccountClient {

    @GetMapping("/api/v1/account/get-external-id")
    ResponseEntity<UserAccountResponseDTO> getUserAccountExternalId();

}
