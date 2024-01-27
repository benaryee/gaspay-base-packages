/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.request;

import com.rancard.utils.MsisdnUtils;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SignInDto implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        if (username != null && MsisdnUtils.isValidPhoneNumber(username)) {
            return MsisdnUtils.phoneNumberFormat(username);
        }
        return username;
    }
}
