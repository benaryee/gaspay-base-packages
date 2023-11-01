package com.rancard.auth.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateAgentDto  implements Serializable {
    private String username;
    private String password;
    private String email;
    private String msisdn;
    private String outletName;
    private String keycloakUserId;
}
