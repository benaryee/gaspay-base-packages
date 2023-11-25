package com.gaspay.campaignservice.controller;

import com.rancard.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PaymentFinancingController {

    public final CampaignService campaignService;


    public ApiResponse<?> createCampaign(CreateCampaignDto createCampaignDto, HttpServletRequest httpServletRequest){

    }

}
