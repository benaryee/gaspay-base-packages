/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.request;

import com.rancard.utils.MsisdnUtils;
import java.io.Serializable;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateAgentDto implements Serializable {
    private String username;
    private String password;
    private String email;
    private String msisdn;
    private String outletName;
    private String keycloakUserId;

    public String getMsisdn() {
        if (msisdn != null) return MsisdnUtils.phoneNumberFormat(msisdn);
        return null;
    }
}
