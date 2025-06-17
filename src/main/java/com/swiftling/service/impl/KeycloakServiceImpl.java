package com.swiftling.service.impl;

import com.swiftling.service.KeycloakService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    @Override
    public String getAccessToken() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            String tokenValue = jwtAuthToken.getToken().getTokenValue();
            return "Bearer " + tokenValue;
        }

        throw new IllegalStateException("Authentication is not of type JwtAuthenticationToken");

    }

    @Override
    public String getLoggedInUserName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return (String) jwtAuth.getTokenAttributes().get("preferred_username");
        }

        if (authentication instanceof KeycloakAuthenticationToken keycloakAuth) {
            return keycloakAuth.getName();
        }

        throw new IllegalStateException("Unsupported authentication type: " + (authentication != null ? authentication.getClass() : "null"));

    }

}
