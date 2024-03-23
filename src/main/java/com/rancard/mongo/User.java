/*(C) Gaspay App 2023-2024 */
package com.rancard.mongo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.dto.payload.Address;
import com.rancard.dto.payload.UserDto;
import com.rancard.enums.Channel;
import com.rancard.enums.UserStatus;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
@CompoundIndex(def = "{'msisdn': 1, 'roles': 1 }", unique = true)
@Slf4j
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
    private Channel registrationChannel;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String inviteToken;
    @Indexed private String msisdn;
    private int successiveFailedAttempts;
    @Indexed private String walletId;
    private boolean resetPassword;
    private LocalDateTime lastLogin;
    private LocalDateTime lastSeen;
    private String keycloakUserId;
    private Address address = new Address();
    private Set<Role> roles = new HashSet<>();
    @DBRef(db = "payment-service")
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    public UserDto toDto() {
        log.info("User toDto {} :", this);
        return UserDto.builder()
                .id(this.getIdString())
                .email(email)
                .msisdn(msisdn)
                .firstname(firstname)
                .lastname(lastname)
                .address(address)
                .inviteToken(inviteToken)
                .roles(
                        (roles != null && !roles.isEmpty())
                                ? roles.stream()
                                        .map(Role::toDto)
                                        .collect(java.util.stream.Collectors.toSet())
                                : new HashSet<>())
                .paymentMethods(
                        (paymentMethods != null && !paymentMethods.isEmpty())
                                ? paymentMethods.stream()
                                        .map(PaymentMethod::toDto)
                                        .collect(java.util.stream.Collectors.toSet())
                                : new HashSet<>())
                .lastLogin(lastLogin)
                .othernames(othernames)
                .walletId(walletId)
                .build();
    }

    public static User fromDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId() != null ?  new ObjectId(userDto.getId()) : null)
                .username(userDto.getEmail())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .othernames(userDto.getOthernames())
                .email(userDto.getEmail())
                .msisdn(userDto.getMsisdn())
                .address(userDto.getAddress())
                .inviteToken(userDto.getInviteToken())
                .lastLogin(userDto.getLastLogin())
                .walletId(userDto.getWalletId())
                .build();
    }
}
