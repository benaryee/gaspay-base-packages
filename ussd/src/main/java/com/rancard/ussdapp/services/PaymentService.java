package com.rancard.ussdapp.services;

import com.rancard.ussdapp.model.dto.TopupRequestDto;
import com.rancard.ussdapp.model.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final WebClient.Builder webClientBuilder;

    public ApiResponse<?> sendPaymentRequestToUser(TopupRequestDto topupRequestDto, String sessionId){
        log.info("[{}] about to send payment request to user with payload : {}",sessionId , topupRequestDto  );

        return webClientBuilder.build().post()
                .uri("lb://payment-service/api/payment/topup")
                .bodyValue(topupRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {})
                .block();
    }
}
