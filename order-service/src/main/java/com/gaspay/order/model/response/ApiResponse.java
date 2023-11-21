package com.gaspay.order.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gaspay.order.model.payload.BaseError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {

    private int code;
    private String message;
    private T data;
    private long duration;
    private String requestId;
    private BaseError error;
}
