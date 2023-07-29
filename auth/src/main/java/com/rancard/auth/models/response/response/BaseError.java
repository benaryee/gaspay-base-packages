package com.rancard.auth.models.response.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseError implements Serializable {

    private int errorCode;
    private String errorMessage;
    private String url;
}
