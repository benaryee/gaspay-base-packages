/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.utils.MsisdnUtils;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {
    private String id;
    private String email;
    private String msisdn;
    private String firstname;
    private String lastname;
    private boolean loggedIn;
    private Address address;
    private LocalDateTime lastLogin;
    private String othernames;
    private String walletId;
    private Set<RoleDto> roles = new HashSet<>();
    private Set<PaymentMethodDto> paymentMethods = new HashSet<>();
    private String familySize;
    private String currentFuelSource;
    private String inviteToken;

    public String getMsisdn() {
        if (msisdn != null) return MsisdnUtils.phoneNumberFormat(msisdn);
        return null;
    }
}
