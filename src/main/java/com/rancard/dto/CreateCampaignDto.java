package com.rancard.dto;


import com.rancard.enums.CampaignType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

@Data
@Slf4j
public class CreateCampaignDto {
    private CampaignType campaignType;
    private String agentId;
    private DateTime startDate;
    private DateTime endDate;

}
