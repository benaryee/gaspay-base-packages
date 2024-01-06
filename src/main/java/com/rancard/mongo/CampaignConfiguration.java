/*(C) Gaspay App 2023 */
package com.rancard.mongo;

import com.rancard.dto.payload.CampaignTarget;
import com.rancard.dto.payload.MilestoneRewardConfig;
import com.rancard.enums.CampaignType;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Document
public class CampaignConfiguration extends BaseMongoModel {
    private String campaignId;

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

    private boolean enforceDailyLimit;
    private double dailyLimit;
    private boolean enforceWeeklyLimit;
    private double weeklyLimit;
    private boolean enforceMonthlyLimit;
    private double monthlyLimit;
    private boolean enforceCampaignLimit;
    private double campaignLimit;

    private List<MilestoneRewardConfig> milestoneRewardConfigs;

    public static CampaignConfiguration fromDto(CampaignConfiguration dto) {
        return CampaignConfiguration.builder()
                .campaignId(dto.getCampaignId())
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
                .enforceDailyLimit(dto.isEnforceDailyLimit())
                .dailyLimit(dto.getDailyLimit())
                .enforceWeeklyLimit(dto.isEnforceWeeklyLimit())
                .weeklyLimit(dto.getWeeklyLimit())
                .enforceMonthlyLimit(dto.isEnforceMonthlyLimit())
                .monthlyLimit(dto.getMonthlyLimit())
                .enforceCampaignLimit(dto.isEnforceCampaignLimit())
                .campaignLimit(dto.getCampaignLimit())
                .milestoneRewardConfigs(dto.getMilestoneRewardConfigs())
                .build();
    }

    public CampaignConfiguration toDto() {
        return CampaignConfiguration.builder()
                .campaignId(campaignId)
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
                .enforceDailyLimit(enforceDailyLimit)
                .dailyLimit(dailyLimit)
                .enforceWeeklyLimit(enforceWeeklyLimit)
                .weeklyLimit(weeklyLimit)
                .enforceMonthlyLimit(enforceMonthlyLimit)
                .monthlyLimit(monthlyLimit)
                .enforceCampaignLimit(enforceCampaignLimit)
                .campaignLimit(campaignLimit)
                .milestoneRewardConfigs(milestoneRewardConfigs)
                .build();
    }
}
