package com.rancard.auth.model.response.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenExceptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -8492006033842690365L;

    private String error;
    private String errorDescription;
}
