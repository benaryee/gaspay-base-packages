/*(C) Gaspay App 2023 */
package com.rancard.mongo;

import com.rancard.dto.payload.Address;
import com.rancard.dto.payload.VendorDto;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode(callSuper = true)
@CompoundIndex(def = "{'name': 1, 'msisdn': 1 }", unique = true)
public class Vendor extends BaseMongoModel {
    private String name;
    private String description;
    private Address address;
    private String msisdn;
    private String email;
    private String website;
    private String logo;
    private String status;
    private String createdBy;
    private String modifiedBy;
    private String walletId;
    @DBRef private Set<Outlet> outlets;

    public static Vendor fromDto(Vendor vendor) {
        return Vendor.builder()
                .name(vendor.getName())
                .description(vendor.getDescription())
                .address(vendor.getAddress())
                .msisdn(vendor.getMsisdn())
                .email(vendor.getEmail())
                .website(vendor.getWebsite())
                .logo(vendor.getLogo())
                .status(vendor.getStatus())
                .createdBy(vendor.getCreatedBy())
                .modifiedBy(vendor.getModifiedBy())
                .outlets(vendor.getOutlets())
                .build();
    }

    public VendorDto toDto() {
        return VendorDto.builder()
                .id(getIdString())
                .name(name)
                .description(description)
                .address(address)
                .msisdn(msisdn)
                .email(email)
                .website(website)
                .logo(logo)
                .status(status)
                .createdBy(createdBy)
                .modifiedBy(modifiedBy)
                .outlets(outlets)
                .build();
    }
}
