/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.dto.payload.Address;
import com.rancard.dto.payload.UserDto;
import java.io.Serializable;
import java.util.Set;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOutletDto implements Serializable {
    private String name;
    private Address address;
    private boolean active;
    private String email;
    private String website;
    private String logo;
    private String description;
    private String createdBy;
    private String vendorId;
    private Set<UserDto> agents;
}
