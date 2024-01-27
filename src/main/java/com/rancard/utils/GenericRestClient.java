/*(C) Gaspay App 2024 */
package com.rancard.utils;

import com.rancard.dto.request.RequestDetails;
import com.rancard.exception.RestTemplateResponseErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class GenericRestClient<T, V> {
    private final RestTemplate restTemplate = new RestTemplate();

    public V execute(RequestDetails requestDetails, T data, Class<V> genericClass)
            throws ResourceAccessException, Exception {

        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<T> entity = new HttpEntity<T>(data, headers);

        log.info("Entity: {}", entity.toString());
        log.info("Request Details: {}", requestDetails.toString());

        ResponseEntity<V> response =
                restTemplate.exchange(
                        requestDetails.getUrl(),
                        requestDetails.getRequestType(),
                        entity,
                        genericClass);
        return response.getBody();
    }
}
