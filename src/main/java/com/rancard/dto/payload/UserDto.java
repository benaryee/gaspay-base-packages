/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.utils.MsisdnUtils;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Set<String> roles;
    private String familySize;
    private String currentFuelSource;

    public String getMsisdn() {
        if (msisdn != null) return MsisdnUtils.phoneNumberFormat(msisdn);
        return null;
    }
}
