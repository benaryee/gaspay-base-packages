package com.rancard.paymentservice.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZeepayApiRequest {

    private String customer_first_name;
    private String customer_last_name;
    private String source_country;
    private String customer_msisdn;
    private String receiver_country;
    private BigDecimal amount;
    private String service_type;
    private String extr_id;
    private String description;
    private String debit_currency;
    private String debit_country;
    private String mno;
    private String callback_url;
    private String transaction_type;

}
