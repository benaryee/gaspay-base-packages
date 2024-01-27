package com.rancard.dto.request;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CreateCampaignDtoTest {

    @Test
    void getStartDate() {

        CreateCampaignDto createCampaignDto = new CreateCampaignDto();
        createCampaignDto.setStartDate(LocalDateTime.of(2021, 10, 10, 10, 10));
        System.out.println(createCampaignDto.getStartDate());
    }
}