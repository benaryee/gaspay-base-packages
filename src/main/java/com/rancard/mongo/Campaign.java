package com.rancard.mongo;

import com.rancard.dto.payload.CampaignDto;
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

    public CampaignDto toDto() {
        return CampaignDto.builder()
                .name(name)
                .customShareUrl(customShareUrl)
                .campaignId(campaignId)
                .startDate(startDate)
                .endDate(endDate)
                .campaignType(campaignType)
                .balance(balance)
                .allocation(allocation)
                .allowedDays(allowedDays)
                .weekdayOpenAt(weekdayOpenAt)
                .weekdayCloseAt(weekdayCloseAt)
                .saturdayOpenAt(saturdayOpenAt)
                .saturdayCloseAt(saturdayCloseAt)
                .sundayOpenAt(sundayOpenAt)
                .sundayCloseAt(sundayCloseAt)
                .campaignTarget(campaignTarget)
                .build();
    }

    public Campaign fromDto(CampaignDto dto) {
        return Campaign.builder()
                .name(dto.getName())
                .customShareUrl(dto.getCustomShareUrl())
                .campaignId(dto.getCampaignId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .campaignType(dto.getCampaignType())
                .balance(dto.getBalance())
                .allocation(dto.getAllocation())
                .allowedDays(dto.getAllowedDays())
                .weekdayOpenAt(dto.getWeekdayOpenAt())
                .weekdayCloseAt(dto.getWeekdayCloseAt())
                .saturdayOpenAt(dto.getSaturdayOpenAt())
                .saturdayCloseAt(dto.getSaturdayCloseAt())
                .sundayOpenAt(dto.getSundayOpenAt())
                .sundayCloseAt(dto.getSundayCloseAt())
                .campaignTarget(dto.getCampaignTarget())
                .build();
    }

}
