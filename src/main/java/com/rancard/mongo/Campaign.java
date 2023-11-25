package com.rancard.mongo;

import com.rancard.enums.CampaignType;
import org.joda.time.DateTime;

import java.time.LocalTime;
import java.util.List;

public class Campaign extends BaseMongoModel{

    private String campaignName;
    private String customShareUrl;
    private String campaignId;
    private DateTime startDate;
    private DateTime endDate;
    private DateTime sendAt;
    private String ussdCode;
    private CampaignType campaignType;
    private Double amountRemaining;
    private Double amountAllocated;
    private List<Integer> allowedDays;
    private LocalTime weekdayOpenAt;
    private LocalTime weekdayCloseAt;
    private LocalTime saturdayOpenAt;
    private LocalTime saturdayCloseAt;
    private LocalTime sundayOpenAt;
    private LocalTime sundayCloseAt;

}
