package com.rancard.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HirePurchaseAgreementDto{
    private String id;
    private String name;
    private BigDecimal amount;
    private boolean isActive;
}
