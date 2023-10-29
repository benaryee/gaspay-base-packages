package com.rancard.paymentservice.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZeepayApiResponse {

    private String code;
    private String message;
    @JsonProperty("zeepay_id")
    private String zeepayId;
    private String amount;
}
