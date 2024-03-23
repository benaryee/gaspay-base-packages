package com.rancard.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToInventoryDto implements Serializable {

    private String outletId;
    private String skuCode;
    private Integer quantity;

}