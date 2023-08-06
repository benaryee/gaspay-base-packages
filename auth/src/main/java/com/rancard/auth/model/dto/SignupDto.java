package com.rancard.auth.model.dto;

import com.rancard.auth.model.enums.Channel;
import com.rancard.auth.model.enums.Role;
import com.rancard.auth.model.payload.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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

        private Address address = new Address();
        private Channel channel;
        private String currentFuelSource;
        private String familySize;
        private List<RoleDto> roles = new ArrayList<>();

}
