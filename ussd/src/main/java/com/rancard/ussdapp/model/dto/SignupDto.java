package com.rancard.ussdapp.model.dto;

import com.rancard.ussdapp.model.enums.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupDto implements Serializable {

        private String username;

        private String email;

        private String phone;
        private String firstName;
        private String lastName;
        private String otherNames;


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
        private String currentFuelSource;
        private int familySize;
        private Channel channel;

        private List<RoleDto> roles = new ArrayList<>();

}
