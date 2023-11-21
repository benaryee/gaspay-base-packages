package com.gaspay.productservice.service;

import com.gaspay.productservice.model.payload.ApiResponse;
import com.gaspay.productservice.model.payload.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final WebClient.Builder webClientBuilder;

    public User getUserAddress(String msisdn, String sessionId) {
        log.info("[{}] about to fetch user with msisdn : {}",sessionId , msisdn );

        ApiResponse<?> apiResponse = webClientBuilder.build().get()
                .uri("lb://auth/api/auth/users/{msisdn}", msisdn)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<User>>() {})
                .block();

        if(apiResponse != null && apiResponse.getData() != null){
            return (User) apiResponse.getData();
        }
        return null;
    }
}
