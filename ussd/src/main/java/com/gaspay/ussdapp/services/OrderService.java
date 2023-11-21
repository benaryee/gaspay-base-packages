package com.gaspay.ussdapp.services;

import com.gaspay.ussdapp.model.dto.CreateOrderDto;
import com.gaspay.ussdapp.model.dto.OrderDto;
import com.gaspay.ussdapp.model.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final WebClient.Builder webClientBuilder;


    public ApiResponse<OrderDto> placeOrder(CreateOrderDto createOrderDto, String sessionId) {
        log.info("[{}] about to send payment request to user with payload : {}", sessionId, createOrderDto);

        return webClientBuilder.build().post()
                .uri("lb://order-service/api/order")
                .bodyValue(createOrderDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<OrderDto>>() {
                })
                .block();
    }
}
