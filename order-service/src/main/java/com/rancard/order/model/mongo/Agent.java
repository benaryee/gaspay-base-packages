package com.rancard.order.model.mongo;

import lombok.Data;

@Data
public class Agent extends User {
    private String outletName;
}
