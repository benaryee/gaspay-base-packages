package com.rancard.ussdapp.config;


import com.rancard.ussdapp.model.payload.OAuthTokenResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class UssdKeycloakAuthConfig {

    private static OAuthTokenResponse accessTokenObject;
    private final RestTemplate restTemplate;

    public UssdKeycloakAuthConfig() {
        this.restTemplate = new RestTemplate();
    }

    @PostConstruct
    public void init(){
        CompletableFuture.runAsync(this::getKeycloakAccessToken);
    }
    public OAuthTokenResponse getAccessTokenObject() {
        return accessTokenObject;
    }

    public void getKeycloakAccessToken() {

        String tokenUrl = "https://keycloak.gaspayapp.com/realms/spring-boot-microservices-realm/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", "spring-cloud-client");
        formData.add("client_secret", "mUh3CecXGznZbpw8XtSgZ4hV2OnVcCg2");
        formData.add("scope", "openid");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<OAuthTokenResponse> responseEntity = restTemplate.postForEntity(tokenUrl, requestEntity, OAuthTokenResponse.class);

        // Check if the response is successful
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            OAuthTokenResponse tokenResponse = responseEntity.getBody();
            log.info("Token retrieved: {}", tokenResponse);
            accessTokenObject = tokenResponse;
        }

    }

}
