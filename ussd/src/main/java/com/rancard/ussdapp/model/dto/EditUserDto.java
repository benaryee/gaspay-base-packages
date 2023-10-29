package com.rancard.ussdapp.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class EditUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4549766876017551019L;

    private String id;

    private String phone;

    private String firstName;

    private String lastName;

    private String otherNames;
    private Set<RoleDto> roles = new HashSet<>();
}
