package com.gaspay.paymentservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().filter(logRequest());
    }


    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Sending request to: {}", clientRequest.url());
            HttpMethod method = clientRequest.method();
            log.info("HTTP method: {}", method);
            HttpHeaders headers = clientRequest.headers();
            headers.forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));

            return Mono.just(clientRequest);
        });
    }
}
