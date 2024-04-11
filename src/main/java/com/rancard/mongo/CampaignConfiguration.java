/*(C) Gaspay App 2023 */
package com.rancard.mongo;

import com.rancard.dto.payload.CampaignTarget;
import com.rancard.dto.payload.MilestoneRewardConfig;
import com.rancard.enums.CampaignType;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

import com.rancard.enums.INCENTIVE_TRIGGER;
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
    private Double balance;
    private Double allocation;
    private List<Integer> allowedDays;
    private LocalTime weekdayOpenAt;
    private LocalTime weekdayCloseAt;
    private LocalTime saturdayOpenAt;
    private LocalTime saturdayCloseAt;
    private LocalTime sundayOpenAt;
    private LocalTime sundayCloseAt;

    private INCENTIVE_TRIGGER incentiveTrigger;
    private boolean enforceDailyLimit;
    private double dailyLimit;
    private boolean enforceWeeklyLimit;
    private double weeklyLimit;
    private boolean enforceMonthlyLimit;
    private double monthlyLimit;
    private boolean enforceCampaignLimit;
    private double campaignLimit;

    private Period minimumSavingPeriod;
    private boolean losePointsOnWithdrawal;


    private List<MilestoneRewardConfig> milestoneRewardConfigs;

    public static CampaignConfiguration fromDto(CampaignConfiguration dto) {
        return CampaignConfiguration.builder()
                .campaignId(dto.getCampaignId())
                .balance(dto.getBalance())
                .allocation(dto.getAllocation())
                .allowedDays(dto.getAllowedDays())
                .weekdayOpenAt(dto.getWeekdayOpenAt())
                .weekdayCloseAt(dto.getWeekdayCloseAt())
                .saturdayOpenAt(dto.getSaturdayOpenAt())
                .saturdayCloseAt(dto.getSaturdayCloseAt())
                .sundayOpenAt(dto.getSundayOpenAt())
                .sundayCloseAt(dto.getSundayCloseAt())
                .enforceDailyLimit(dto.isEnforceDailyLimit())
                .dailyLimit(dto.getDailyLimit())
                .enforceWeeklyLimit(dto.isEnforceWeeklyLimit())
                .weeklyLimit(dto.getWeeklyLimit())
                .enforceMonthlyLimit(dto.isEnforceMonthlyLimit())
                .monthlyLimit(dto.getMonthlyLimit())
                .enforceCampaignLimit(dto.isEnforceCampaignLimit())
                .campaignLimit(dto.getCampaignLimit())
                .incentiveTrigger(dto.getIncentiveTrigger())
                .milestoneRewardConfigs(dto.getMilestoneRewardConfigs())
                .build();
    }

    public CampaignConfiguration toDto() {
        return CampaignConfiguration.builder()
                .campaignId(campaignId)
                .balance(balance)
                .allocation(allocation)
                .allowedDays(allowedDays)
                .weekdayOpenAt(weekdayOpenAt)
                .weekdayCloseAt(weekdayCloseAt)
                .saturdayOpenAt(saturdayOpenAt)
                .saturdayCloseAt(saturdayCloseAt)
                .sundayOpenAt(sundayOpenAt)
                .sundayCloseAt(sundayCloseAt)
                .enforceDailyLimit(enforceDailyLimit)
                .incentiveTrigger(incentiveTrigger)
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
