package com.rancard.auth.model.response.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ApiResponse<T> implements Serializable {

    private int code;
    private String message;
    private String requestId;
    private T data;
    private BaseError error;

}
