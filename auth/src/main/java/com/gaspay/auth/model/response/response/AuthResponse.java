package com.gaspay.auth.model.response.response;

import com.gaspay.auth.model.dto.UserDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class AuthResponse implements Serializable {
//    private UserDetails user;
    private UserDto user;
    private String token;

    public AuthResponse(UserDto user, String token) {
        this.user = user;
        this.token = token;
    }

    // Getters and setters
}
