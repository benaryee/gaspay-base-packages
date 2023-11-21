package com.gaspay.paymentservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CallbackRequest implements Serializable {

    @JsonProperty("zeepay_id")
    private String paymentId;
    private String reference;
    private String status;
    private int code;
    private String message;
    @JsonProperty("gateway_id")
    private String gatewayId;

}
