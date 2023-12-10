package com.rancard.dto.request;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class CreditWalletDto implements Serializable {

    private String id;
    private BigDecimal amount;
}
