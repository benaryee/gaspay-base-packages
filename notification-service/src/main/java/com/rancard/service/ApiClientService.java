package com.rancard.service;

import com.rancard.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;


@Service
public class ApiClientService {

    private final Logger logger = LoggerFactory.getLogger(ApiClientService.class);

    private final String webhookUrl = "https://hooks.slack.com/services/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";

    public String getRequest(String requestUrl, String requestParams, String sessionId)  {

        long start = new Date().getTime();

        logger.info("[ " + sessionId + " ] GET: making request to url  "
                + (StringUtils.isNotBlank(requestUrl) ? requestUrl : "None"));

        if (!StringUtils.isAnyBlank(requestUrl )) {

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            logger.info("[ " + sessionId + " ] about to call url with payload: " + requestParams);

            RestTemplate restTemplate = new RestTemplate(HttpUtils.getTrustAllRequestFactory());

            HttpEntity<Object> entity = new HttpEntity<>(headers);
            logger.info("Request Params : " + requestParams);
            requestUrl += requestParams;
            logger.info("Http Entity : " + entity);

            ResponseEntity<String> responseEntity = null;
            try {
                responseEntity = restTemplate.exchange(requestUrl,
                        HttpMethod.GET, entity, String.class);
            } catch (Exception e) {
                logger.error("[" + sessionId + "]  api call error " + e.getMessage());
                logger.info("["+sessionId+"] about to send notification to slack");


                String slackMessage = "Message : " + e.getMessage().replaceAll("\"", "'") +
                        "\nCause : " + e.getStackTrace();

                return null;
            }

            logger.info("[ " + sessionId + " ] response code from api call : " + responseEntity.getStatusCodeValue());

            if (responseEntity.getStatusCodeValue() == 200) {

                boolean isResponseReturned = (responseEntity.getBody() != null);

                logger.info("[ " + sessionId + " ] Response from api call "
                        + (new Date().getTime() - start) + "(mil seconds) time elapsed"
                        + " response from: " + requestUrl
                        + " is: " + (isResponseReturned ? responseEntity.getBody() : " None"));

                if (isResponseReturned) {

                    String responseString = responseEntity.getBody();

                    logger.info("[ " + sessionId + " ] post response from url: " + responseString);

                    return responseString;
                }
            }else{
                String slackMessage = "Message :POST Request to url "+ requestUrl +"didn't return status code 200";
            }
        }

        return null;
    }



}
