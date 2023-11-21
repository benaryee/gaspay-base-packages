package com.gaspay.ussdapp.services;

import com.gaspay.ussdapp.config.UssdKeycloakAuthConfig;
import com.gaspay.ussdapp.model.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final WebClient.Builder webClientBuilder;

    private final UssdKeycloakAuthConfig ussdKeycloakAuthConfig;

    public ProductResponse getCylinderTypesAvailable(String sessionId) {

        ProductResponse productsResponse = webClientBuilder.build().get()
                .uri("lb://product-service/api/product/get-by-name?name=Cylinder")
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();

        log.info("[{}] Response from product service : {}",sessionId, productsResponse);
        return Objects.requireNonNull(productsResponse);
    }
}
