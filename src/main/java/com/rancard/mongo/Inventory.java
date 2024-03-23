package com.rancard.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Inventory  extends BaseMongoModel {

    private String vendorId;
    private String outletId;
    private String skuCode;
    private Integer quantity;
}