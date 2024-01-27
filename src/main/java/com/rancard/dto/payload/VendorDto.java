/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.mongo.Outlet;
import com.rancard.utils.MsisdnUtils;
import java.io.Serializable;
import java.util.Set;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorDto implements Serializable {
    private String id;
    private String name;
    private Address address;
    private String msisdn;
    private String email;
    private String website;
    private String logo;
    private String description;
    private String status;
    private String createdBy;
    private String modifiedBy;
    private String created;
    private String modified;

    @DBRef private Set<Outlet> outlets;
    private String walletId;

    public String getMsisdn() {
        if (msisdn != null) return MsisdnUtils.phoneNumberFormat(msisdn);
        return null;
    }
}
