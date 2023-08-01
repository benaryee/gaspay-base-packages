package com.rancard.ussdapp.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
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