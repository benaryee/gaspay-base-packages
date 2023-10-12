package com.rancard.ussdapp.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Variant implements Serializable {
    private Double price;
    private Double weight;
    private Double height;
    private String name;
    private int quantity;
    private int stock=-1;
}
