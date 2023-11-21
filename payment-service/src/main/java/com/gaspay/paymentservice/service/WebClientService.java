package com.gaspay.paymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebClientService {

    private final WebClient.Builder webClientBuilder;
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public <T> T makeApiCall(String uri, HttpMethod method, MultiValueMap<String, String> queryParams,
                             HttpHeaders headers, Object requestBody, ParameterizedTypeReference<T> responseType) {

        WebClient webClient = webClientBuilder.build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(uri);
        if (queryParams != null) {
            uriComponentsBuilder.queryParams(queryParams);
        }


        WebClient.RequestBodySpec requestSpec = webClient.method(method)
                .uri(uriComponentsBuilder.build().toUri());

        if (headers != null) {
            requestSpec.headers(httpHeaders -> httpHeaders.addAll(headers));
        }

        WebClient.ResponseSpec responseSpec = null;

        if (requestBody != null) {
            assert headers != null;
            if(Objects.equals(headers.getContentType(), MediaType.APPLICATION_FORM_URLENCODED)){
                responseSpec = requestSpec.body(BodyInserters.fromFormData(convertToFormData(requestBody))).retrieve();
            } else if (Objects.equals(headers.getContentType(),MediaType.APPLICATION_JSON)) {
                responseSpec = requestSpec.body(BodyInserters.fromValue(requestBody)).retrieve();
            }else{
                responseSpec = requestSpec.body(BodyInserters.fromValue(requestBody)).retrieve();
            }
            log.info("Response spec : {}",responseSpec.bodyToMono(responseType).toString());

        } else {
            responseSpec = requestSpec.retrieve();
            log.info("Response spec : {}",responseSpec.bodyToMono(responseType).toString());
        }

        return responseSpec.bodyToMono(responseType).block();
    }


    public <T> T makeZeepayApiCall(String uri, HttpMethod method, MultiValueMap<String, String> queryParams,
                             HttpHeaders headers, Object requestBody, ParameterizedTypeReference<T> responseType) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth2Client.setDefaultClientRegistrationId("CUSTOM_PROVIDER");

        WebClient webClient = webClientBuilder
                .apply(oauth2Client.oauth2Configuration())
                .build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(uri);
        if (queryParams != null) {
            uriComponentsBuilder.queryParams(queryParams);
        }


        WebClient.RequestBodySpec requestSpec = webClient.method(method)
                .uri(uriComponentsBuilder.build().toUri());

        if (headers != null) {
            requestSpec.headers(httpHeaders -> httpHeaders.addAll(headers));
        }

        WebClient.ResponseSpec responseSpec = null;

        if (requestBody != null) {
            assert headers != null;
            if(Objects.equals(headers.getContentType(), MediaType.APPLICATION_FORM_URLENCODED)){
                responseSpec = requestSpec.body(BodyInserters.fromFormData(convertToFormData(requestBody))).retrieve();
            } else if (Objects.equals(headers.getContentType(),MediaType.APPLICATION_JSON)) {
                responseSpec = requestSpec.body(BodyInserters.fromValue(requestBody)).retrieve();
            }else{
                responseSpec = requestSpec.body(BodyInserters.fromValue(requestBody)).retrieve();
            }
            log.info("Response spec : {}",responseSpec.bodyToMono(responseType).toString());

        } else {
            responseSpec = requestSpec.retrieve();
            log.info("Response spec : {}",responseSpec.bodyToMono(responseType).toString());
        }

        return responseSpec.bodyToMono(responseType).block();
    }

    private MultiValueMap<String, String> convertToFormData(Object requestBody) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        // Use reflection to dynamically extract fields from the POJO and add to the formData
        Field[] fields = requestBody.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            try {
                formData.add(field.getName(), String.valueOf(field.get(requestBody)));
            } catch (IllegalAccessException e) {
                // Handle exception if needed
            }
        });

        return formData;
    }
}
