package com.rancard.auth.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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