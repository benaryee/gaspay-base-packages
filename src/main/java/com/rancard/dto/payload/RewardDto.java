/*(C) Gaspay App 2024 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.RewardGroup;
import com.rancard.enums.RewardPoolType;
import com.rancard.enums.RewardType;
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
public class RewardDto {

    private String id;
    private double amount;
    private RewardType type;
    private String rewardId;
    private String campaignId;
    private RewardGroup group;
    private RewardPoolType poolType;
    private List<String> stations;
    private Integer limitPerUser;
    private Integer limitPerCampaign;
    private Integer limitPerStation;
    private double pointValue;
    private double pointConversionRate;
    private Integer limitPerWeek;
    private Integer limitPerMonth;
    private int limit;
    private boolean status;
    private RewardConfiguration rewardConfiguration;
    private String description;
}
