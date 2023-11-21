package com.gaspay.ussdapp.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Variant implements Serializable {
    private BigDecimal price;
    private Double weight;
    private Double height;
//    private String name;
    private int quantity;
    private int stock=-1;
}
