package com.rancard.auth.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class EditUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4549766876017551019L;

    @NotNull
    @NotEmpty
    private String id;

    @NotNull
    @NotEmpty
    private String phone;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    private String otherNames;

    @NotNull
    private Set<RoleDto> roles = new HashSet<>();
}
