package com.rancard.paymentservice.service;

import com.rancard.paymentservice.model.domain.OAuthTokenResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class ZeepayOAuth2Config{
//
//    private final WebClient.Builder webClient;
//    private static OAuthTokenResponse accessTokenObject;
//
//    @PostConstruct
//    public void init(){
//        CompletableFuture.runAsync(this::getZeepayAccessToken);
//    }
//    public OAuthTokenResponse getAccessTokenObject() {
//        return accessTokenObject;
//    }
//
//    public void getZeepayAccessToken() {
//        OAuthTokenResponse tokenResponse = webClient.build().post()
//                .uri("https://test.digitaltermination.com/oauth/token")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .body(BodyInserters.fromFormData("grant_type", "password")
//                        .with("client_id", "243")
//                        .with("client_secret", "Xxdo3jAmR1RI5x3eo6LmyQtOsaqZQmZcrJMJ56WJ")
//                        .with("username", "bernard.aryee@rancard.com")
//                        .with("password", "KHQAEF9RYErZ7wi")
//                        .with("scope", "*"))
//                .retrieve()
//                .bodyToMono(OAuthTokenResponse.class)
//                .block();
//
//        if (tokenResponse != null) {
//            log.info("Token retrieved : {}",tokenResponse);
//            accessTokenObject = tokenResponse;
//        }
//    }

}
