package com.gaspay.auth.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {

    private int code;

    private String name;
    private String description;
    private String createdBy;
}
