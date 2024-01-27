/*(C) Gaspay App 2024 */
package com.rancard.dto.request;

import com.rancard.dto.payload.RewardConfiguration;
import com.rancard.enums.RewardGroup;
import com.rancard.enums.RewardPoolType;
import com.rancard.enums.RewardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateRewardDto {

    private double amount;
    private RewardType type;
    private RewardGroup group;
    private double pointValue;
    private double pointConversionRate;
    private RewardPoolType poolType;
    private RewardConfiguration rewardConfiguration;
    private String description;
}
