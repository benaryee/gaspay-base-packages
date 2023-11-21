package com.gaspay.ussdapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig2 {

    @Bean
    public WebClient.Builder webClientBuilder2() {
        return WebClient.builder();
    }
}
