package com.rancard.ussdapp.model.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EditPasswordDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6091522525731613889L;

    private String id;

    private String password;
}