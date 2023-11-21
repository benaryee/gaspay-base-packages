package com.gaspay.auth.model.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EditPasswordDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6091522525731613889L;

    @NotNull
    @NotEmpty
    private String id;


    @NotNull
    @Size(min= 4, max = 32)
    private String password;
}