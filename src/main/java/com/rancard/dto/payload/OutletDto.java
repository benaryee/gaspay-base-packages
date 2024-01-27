/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class OutletDto implements Serializable {
    private String id;
    private String name;
    private Address address;
    private String email;
    private String msisdn;
    private String website;
    private String logo;
    private String description;
    private boolean active;
    private String walletId;
    private String createdBy;
    private String modifiedBy;
    private String created;
    private String modified;
    private String vendorId;

    @DBRef private Set<UserDto> managers;
    @DBRef private Set<UserDto> agents;
    private Set<UserDto> supervisors;
    private Set<UserDto> cashiers;
    private Set<UserDto> auditors;
    private Set<UserDto> accountants;
    private Set<UserDto> customerServicePersonnel;
}
