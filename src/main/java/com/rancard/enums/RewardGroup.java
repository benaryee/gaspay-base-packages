package com.rancard.enums;


import java.io.Serializable;

public enum RewardGroup implements Serializable {

    PHYSICAL("PHYSICAL", 1),
    DIGITAL("DIGITAL", 2),
    TIME_BOUND("TIME_BOUND",3);

    private final String id;
    private final int rewardGroupId;

    RewardGroup(String id, int rewardGroupId) {
        this.id = id;
        this.rewardGroupId = rewardGroupId;
    }

    public String getId() {
        return id;
    }

    public int getRewardGroupId() {
        return rewardGroupId;
    }

    public static RewardGroup getRewardGroup(RewardType rewardType){
        switch (rewardType.getRewardId()){
            case 1: case 2:case 3: case 7:
                return RewardGroup.DIGITAL;
            case 13:
                return RewardGroup.TIME_BOUND;
            default:
                return RewardGroup.PHYSICAL;
        }
    }
}
