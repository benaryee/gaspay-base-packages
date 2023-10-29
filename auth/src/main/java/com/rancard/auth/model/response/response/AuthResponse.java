package com.rancard.auth.model.response.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class AuthResponse implements Serializable {
    private UserDetails user;
    private String token;

    public AuthResponse(UserDetails user, String token) {
        this.user = user;
        this.token = token;
    }

    // Getters and setters
}
