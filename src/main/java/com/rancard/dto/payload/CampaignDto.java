package com.rancard.dto.payload;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.CampaignType;
import com.rancard.payload.CampaignTarget;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CampaignDto implements Serializable {
    private String name;
    private String customShareUrl;
    private String campaignId;
    private DateTime startDate;
    private DateTime endDate;
    private DateTime sendAt;
    private String ussdCode;
    private CampaignType campaignType;
    private Double balance;
    private Double allocation;
    private List<Integer> allowedDays;
    private LocalTime weekdayOpenAt;
    private LocalTime weekdayCloseAt;
    private LocalTime saturdayOpenAt;
    private LocalTime saturdayCloseAt;
    private LocalTime sundayOpenAt;
    private LocalTime sundayCloseAt;
    private CampaignTarget campaignTarget;
}
