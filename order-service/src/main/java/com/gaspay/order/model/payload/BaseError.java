package com.gaspay.order.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseError implements Serializable {

    private int errorCode;
    private String errorMessage;
    private String url;
}
