package com.rancard.ussdapp.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RoleDto implements Serializable {

    private int code;

    private String name;
    private String description;
    private String createdBy;
}
