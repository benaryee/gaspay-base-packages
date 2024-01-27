/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.CampaignType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CampaignDto implements Serializable {
    private String id;
    private String name;
    private String customShareUrl;
    private String campaignId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime sendAt;
    private String rewardId;
    private String ussdCode;
    private boolean active;
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
