/*(C) Gaspay App 2024 */
package com.rancard.mongo;

import com.rancard.dto.payload.RewardConfiguration;
import com.rancard.dto.payload.RewardDto;
import com.rancard.enums.RewardGroup;
import com.rancard.enums.RewardPoolType;
import com.rancard.enums.RewardType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode(callSuper = true)
public class Reward extends BaseMongoModel {
    private double amount;
    private RewardType type;
    private String rewardId;
    private String campaignId;
    private RewardGroup group;
    private double pointValue;
    private double pointConversionRate;
    private RewardPoolType poolType;
    private List<String> stations;
    private Integer limitPerUser;
    private Integer limitPerCampaign;
    private Integer limitPerStation;
    private Integer limitPerWeek;
    private Integer limitPerMonth;
    private RewardConfiguration rewardConfiguration;
    private String description;
    private int limit;
    private boolean status;

    public static Reward fromDto(RewardDto rewardDto) {
        return rewardDto != null
                ? Reward.builder()
                        .id(new ObjectId(rewardDto.getId()))
                        .amount(rewardDto.getAmount())
                        .type(rewardDto.getType())
                        .rewardId(rewardDto.getRewardId())
                        .campaignId(rewardDto.getCampaignId())
                        .group(rewardDto.getGroup())
                        .poolType(rewardDto.getPoolType())
                        .stations(rewardDto.getStations())
                        .limitPerUser(rewardDto.getLimitPerUser())
                        .limitPerCampaign(rewardDto.getLimitPerCampaign())
                        .limitPerStation(rewardDto.getLimitPerStation())
                        .limitPerWeek(rewardDto.getLimitPerWeek())
                        .limitPerMonth(rewardDto.getLimitPerMonth())
                        .limit(rewardDto.getLimit())
                        .pointValue(rewardDto.getPointValue())
                        .rewardConfiguration(rewardDto.getRewardConfiguration())
                        .pointConversionRate(rewardDto.getPointConversionRate())
                        .status(rewardDto.isStatus())
                        .description(rewardDto.getDescription())
                        .build()
                : null;
    }

    public RewardDto toDto() {
        return RewardDto.builder()
                .id(getIdString())
                .amount(amount)
                .type(type)
                .rewardId(rewardId)
                .campaignId(campaignId)
                .group(group)
                .poolType(poolType)
                .stations(stations)
                .limitPerUser(limitPerUser)
                .limitPerCampaign(limitPerCampaign)
                .limitPerStation(limitPerStation)
                .limitPerWeek(limitPerWeek)
                .limitPerMonth(limitPerMonth)
                .pointValue(pointValue)
                .rewardConfiguration(rewardConfiguration)
                .pointConversionRate(pointConversionRate)
                .limit(limit)
                .description(description)
                .status(status)
                .build();
    }
}
