package com.rancard.auth.model.response.response;

import com.rancard.auth.model.dto.UserDto;
import com.rancard.auth.model.mongo.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

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
