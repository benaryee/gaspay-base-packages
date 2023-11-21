package com.gaspay.paymentservice.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
public class ApiResponse<T> implements Serializable {

    private int code;
    private String message;
    private String requestId;
    private T data;
    private BaseError error;

}