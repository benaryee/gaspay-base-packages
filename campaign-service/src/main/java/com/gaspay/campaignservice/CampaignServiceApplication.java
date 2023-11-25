package com.gaspay.campaignservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class CampaignServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampaignServiceApplication.class, args);
    }

}
