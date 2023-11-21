package com.gaspay.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Variant implements Serializable {
    private Double price;
    private Double weight;
    private Double height;
    private String name;
    private int quantity;
    private int stock=-1;
}
