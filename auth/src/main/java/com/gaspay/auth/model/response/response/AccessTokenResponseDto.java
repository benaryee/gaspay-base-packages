package com.gaspay.auth.model.response.response;

import lombok.Data;
import org.keycloak.representations.AccessTokenResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AccessTokenResponseDto extends AccessTokenResponse implements Serializable {
    private List<String> roles = new ArrayList<>();
}
