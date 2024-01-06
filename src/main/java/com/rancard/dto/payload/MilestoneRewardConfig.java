/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.rancard.enums.RewardMode;
import java.util.List;
import lombok.Data;

@Data
public class MilestoneRewardConfig {
    private Integer mileStoneStage;

    // TODO -- Change idea of rewardIds to rewardIdWeeklyLimits
    private List<String> rewardIds;
    private List<Integer> rewardIdWeeklyLimits;

    private RewardMode rewardMode;
    private Integer winByEntry;
    private int minimumForMilestone;
    private int maximumForMilestone;
    private double totalRewardAmountForMilestone;
    private double totalAllowableRewardAmountForMilestone;
    private int allowedNumberOfWins;
}
