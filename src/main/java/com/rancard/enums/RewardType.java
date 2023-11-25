package com.rancard.enums;

import java.util.Arrays;

public enum RewardType {

    CASH("CASH",1),
    MOMO("MOMO", 2),
    AIRTIME("AIRTIME",3),
    LOYALTY_POINTS("LOYALTY_POINTS",4),
    GIFT_VOUCHER("GIFT_VOUCHER",5),
    ITEM("ITEM",6),
    FREE_DELIVERY("FREE_DELIVERY" , 7);

    private final String id;
    private final int rewardId;

    RewardType(String id, int rewardId) {
        this.id = id;
        this.rewardId = rewardId;
    }

    public String getId() {
        return this.id;
    }

    public int getRewardId() {
        return this.rewardId;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public static RewardType fromId(String id) {
        RewardType rewardType = null;
        if ((!id.isBlank())) {
            rewardType = Arrays.stream(RewardType.values()).filter(r -> {
                        return id.equalsIgnoreCase(r.id);
                    })
                    .findFirst().orElse(null);
        }
        return rewardType;
    }
}
