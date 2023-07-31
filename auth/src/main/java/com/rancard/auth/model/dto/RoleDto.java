package com.rancard.auth.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {

    private int code;

    private String name;
    private String description;
    private String createdBy;
}
