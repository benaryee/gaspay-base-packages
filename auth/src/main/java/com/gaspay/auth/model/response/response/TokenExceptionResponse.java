package com.gaspay.auth.model.response.response;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TokenExceptionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -8492006033842690365L;

    private String error;
    private String errorDescription;
}
