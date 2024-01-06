/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.dto.payload.Address;
import com.rancard.mongo.Outlet;
import com.rancard.utils.MsisdnUtils;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateVendorDto implements Serializable {
    private String name;
    private Address address;
    private String msisdn;
    private String email;
    private String website;
    private String logo;
    private String description;
    private String status;

    @DBRef private Set<Outlet> outlets;
    private String walletId;

    public String getMsisdn() {
        return MsisdnUtils.phoneNumberFormat(msisdn);
    }
}
