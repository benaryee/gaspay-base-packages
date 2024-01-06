package com.rancard.mongo;


import com.rancard.enums.RewardGroup;
import com.rancard.enums.RewardPoolType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode(callSuper = true)
public class Reward extends BaseMongoModel {
    private double amount;
    private String type = "AIRTIME";
    private String rewardId;
    private String campaignId;
    private RewardGroup group;
    private RewardPoolType poolType;
    private List<String> stations;
    private Integer limitPerUser;
    private Integer limitPerCampaign;
    private Integer limitPerStation;
    private Integer limitPerWeek;
    private Integer limitPerMonth;
    private int limit;
    private boolean status;
}

