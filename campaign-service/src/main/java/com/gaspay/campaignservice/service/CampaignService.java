package com.gaspay.campaignservice.service;

import com.rancard.dto.request.CreateCampaignDto;
import com.rancard.mongo.Campaign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampaignService {

    public Campaign createCampaign(CreateCampaignDto createCampaignDto, String sessionId) {
        log.info("[{}] about to create campaign with details : {}", sessionId, createCampaignDto.toString());
        return Campaign.builder()
                .name(createCampaignDto.getName())
                .startDate(createCampaignDto.getStartDate())
                .endDate(createCampaignDto.getEndDate())
                .campaignType(createCampaignDto.getCampaignType())
                .campaignTarget(createCampaignDto.getCampaignTarget())
                .build();
    }
}
