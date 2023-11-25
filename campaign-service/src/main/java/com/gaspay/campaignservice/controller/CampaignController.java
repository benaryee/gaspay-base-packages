package com.gaspay.campaignservice.controller;

import com.gaspay.campaignservice.service.CampaignService;

import com.rancard.dto.request.CreateCampaignDto;
import com.rancard.mongo.Campaign;
import com.rancard.response.ApiResponse;
import com.rancard.utils.ApiUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/campaign")
public class CampaignController {

    public final CampaignService campaignService;


    @PostMapping
    public ApiResponse<?> createCampaign(CreateCampaignDto createCampaignDto, HttpServletRequest httpServletRequest){
        String sessionId = httpServletRequest.getSession().getId();
        log.info("[{}] about to create campaign with details : {}", sessionId, createCampaignDto.toString());
        Campaign campaign = campaignService.createCampaign(createCampaignDto, sessionId);
        return ApiUtils.wrapInApiResponse(campaign.toDto(), sessionId);
    }

}
