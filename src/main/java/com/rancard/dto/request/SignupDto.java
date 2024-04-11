/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.request;

import com.rancard.dto.payload.Address;
import com.rancard.dto.payload.RoleDto;
import com.rancard.enums.Channel;
import com.rancard.utils.MsisdnUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SignupDto implements Serializable {

    private String username;
    private String email;
    private String msisdn;
    private String firstName;
    private String lastName;
    private String otherNames;

    private String password;
    private String confirmPassword;
    private String inviteToken;
    private String couponCode;

    private Address address = new Address();

    private Channel channel;
    private String currentFuelSource;
    private String familySize;

    private List<RoleDto> roles = new ArrayList<>();

    public String getUsername() {
        if (username != null && MsisdnUtils.isValidPhoneNumber(username))
            return MsisdnUtils.phoneNumberFormat(username);
        return null;
    }

    public String getMsisdn() {
        if (msisdn != null && MsisdnUtils.isValidPhoneNumber(msisdn))
            return MsisdnUtils.phoneNumberFormat(msisdn);
        return null;
    }
}
