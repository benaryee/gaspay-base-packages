/*(C) Gaspay App 2023 */
package com.rancard.mongo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.dto.payload.Address;
import com.rancard.dto.payload.UserDto;
import com.rancard.enums.UserStatus;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
@CompoundIndex(def = "{'msisdn': 1, 'roles': 1 }", unique = true)
public class User extends BaseMongoModel {

    @Indexed private String username;
    private String firstname;
    private String othernames;
    private UserStatus userStatus;

    private String lastname;
    @Indexed private String email;
    private String familySize;
    private String currentFuelSource;
    private String password;
    private boolean isActive;
    private String street;
    private String city;
    private String state;
    private String postalCode;

    @Indexed private String msisdn;
    private int successiveFailedAttempts;

    @Indexed private String walletId;

    private boolean resetPassword;
    private LocalDateTime lastLogin;
    private LocalDateTime lastSeen;

    private String keycloakUserId;
    private Address address = new Address();
    private Set<Role> roles;

    public UserDto toDto() {
        return UserDto.builder()
                .id(this.getIdString())
                .email(email)
                .msisdn(msisdn)
                .firstname(firstname)
                .lastname(lastname)
                .address(address)
                .roles(
                        !roles.isEmpty()
                                ? roles.stream()
                                        .map(Role::getName)
                                        .collect(java.util.stream.Collectors.toSet())
                                : new HashSet<>())
                .lastLogin(lastLogin)
                .othernames(othernames)
                .walletId(walletId)
                .build();
    }

    public static User fromDto(UserDto userDto) {
        return User.builder()
                .username(userDto.getEmail())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .othernames(userDto.getOthernames())
                .email(userDto.getEmail())
                .msisdn(userDto.getMsisdn())
                .address(userDto.getAddress())
                .lastLogin(userDto.getLastLogin())
                .walletId(userDto.getWalletId())
                .build();
    }
}
