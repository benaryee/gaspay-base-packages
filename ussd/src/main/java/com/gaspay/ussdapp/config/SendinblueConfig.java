package com.gaspay.ussdapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import sendinblue.ApiClient;
import sendinblue.Configuration;


@org.springframework.context.annotation.Configuration
public class SendinblueConfig {

  @Value("${sendinblue.api.key}")
  private String apiKey;

  @Bean
  public ApiClient sendinblueApiClient() {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setApiKey(apiKey);
    return defaultClient;
  }

}
