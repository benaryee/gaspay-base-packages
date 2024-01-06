/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import com.rancard.dto.payload.Address;
import com.rancard.dto.payload.RoleDto;
import com.rancard.utils.MsisdnUtils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EditUserDto implements Serializable {

    @Serial private static final long serialVersionUID = -4549766876017551019L;

    @NotNull @NotEmpty private String id;

    @NotNull @NotEmpty private String msisdn;

    @NotNull @NotEmpty private String firstName;

    @NotNull @NotEmpty private String lastName;

    private String otherNames;

    private Address address;

    @NotNull private Set<RoleDto> roles = new HashSet<>();

    public String getMsisdn() {
        return MsisdnUtils.phoneNumberFormat(msisdn);
    }
}
