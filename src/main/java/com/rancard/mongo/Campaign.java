package com.rancard.mongo;

import com.rancard.enums.CampaignType;
import com.rancard.payload.CampaignTarget;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.List;

@Data

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Document
public class Campaign extends BaseMongoModel{

    private String name;
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
    private CampaignTarget campaignTarget;

}
