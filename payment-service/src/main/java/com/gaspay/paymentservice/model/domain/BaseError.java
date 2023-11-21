package com.gaspay.paymentservice.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseError implements Serializable {

    private int errorCode;
    private String errorMessage;
    private String url;
}
