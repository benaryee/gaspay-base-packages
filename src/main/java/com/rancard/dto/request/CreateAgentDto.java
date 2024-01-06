/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import com.rancard.utils.MsisdnUtils;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAgentDto implements Serializable {
    private String username;
    private String password;
    private String email;
    private String msisdn;
    private String outletName;
    private String keycloakUserId;

    public String getMsisdn() {
        return MsisdnUtils.phoneNumberFormat(msisdn);
    }
}
