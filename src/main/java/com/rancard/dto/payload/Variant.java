/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class Variant implements Serializable {
    private String id;
    private BigDecimal price;
    private Double weight;
    private Double height;
    private String name;
    private int quantity;
    private int stock = -1;
}
