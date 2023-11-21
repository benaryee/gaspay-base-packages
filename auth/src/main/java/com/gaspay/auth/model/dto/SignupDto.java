package com.gaspay.auth.model.dto;

import com.gaspay.auth.model.enums.Channel;
import com.gaspay.auth.model.payload.Address;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
