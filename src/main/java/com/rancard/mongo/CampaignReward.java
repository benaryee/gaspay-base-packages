package com.rancard.mongo;

import com.rancard.enums.RewardGroup;
import com.rancard.enums.RewardPoolType;
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
public class CampaignReward extends BaseMongoModel {
    private double amount;
    private String type = "AIRTIME";
    private String rewardId;
    private String campaignId;
    private RewardGroup group;
    private RewardPoolType poolType;
    private Integer limitPerUser;
    private Integer limitPerCampaign;
    private Integer limitPerOutlet;
    private Integer limitPerWeek;
    private int limit;
}
