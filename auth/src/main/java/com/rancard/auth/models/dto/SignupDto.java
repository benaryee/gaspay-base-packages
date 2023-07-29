package com.rancard.auth.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SignupDto implements Serializable {

        private String username;

        @Email
        private String email;

        private String phone;
        private String firstName;
        private String lastName;
        private String otherNames;

        @NotNull
        @Size(min = 4, max = 32)
        private String password;
        private String confirmPassword;

        private String street;
        private String city;
        private String state;
        private String postalCode;
        private String location;
        private String longitude;
        private String latitude;
        private String ghanaPostGps;

        private List<RoleDto> roles = new ArrayList<>();

}
