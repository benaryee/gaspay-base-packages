/*(C) Gaspay App 2023-2024 */
package com.rancard.mongo;

import com.rancard.dto.payload.Address;
import com.rancard.dto.payload.OutletDto;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode(callSuper = true)
public class Outlet extends BaseMongoModel {
    private String name;
    @Indexed private Address address;
    @Indexed private String msisdn;
    private String email;
    private String website;
    private String logo;
    @Indexed private boolean active;
    @DBRef private Set<User> managers;
    @DBRef @Indexed private Set<User> agents;
    private Set<User> supervisors;
    private Set<User> cashiers;
    private Set<User> auditors;
    private Set<User> accountants;
    @Indexed private String walletId;
    private Set<User> customerServicePersonnel;
    @Indexed private String vendorId;

    public OutletDto toDto() {
        return OutletDto.builder()
                .id(getIdString())
                .name(name)
                .address(address)
                .email(email)
                .website(website)
                .logo(logo)
                .walletId(walletId)
                .active(active)
                .managers(
                        (managers != null && !managers.isEmpty())
                                ? managers.stream()
                                        .map(User::toDto)
                                        .collect(java.util.stream.Collectors.toSet())
                                : new HashSet<>())
                .agents(
                        (agents != null && !agents.isEmpty())
                                ? agents.stream()
                                        .map(User::toDto)
                                        .collect(java.util.stream.Collectors.toSet())
                                : new HashSet<>())
                .customerServicePersonnel(
                        (customerServicePersonnel != null && !customerServicePersonnel.isEmpty())
                                ? customerServicePersonnel.stream()
                                        .map(User::toDto)
                                        .collect(java.util.stream.Collectors.toSet())
                                : new HashSet<>())
                .vendorId(vendorId)
                .build();
    }

    public static Outlet fromDto(OutletDto outletDto) {
        return Outlet.builder()
                .id(new ObjectId(outletDto.getId()))
                .name(outletDto.getName())
                .address(outletDto.getAddress())
                .email(outletDto.getEmail())
                .website(outletDto.getWebsite())
                .walletId(outletDto.getWalletId())
                .logo(outletDto.getLogo())
                .vendorId(outletDto.getVendorId())
                .active(outletDto.isActive())
                .managers(
                        (outletDto.getManagers() != null && !outletDto.getManagers().isEmpty())
                                ? outletDto.getManagers().stream()
                                        .map(User::fromDto)
                                        .collect(java.util.stream.Collectors.toSet())
                                : new HashSet<>())
                .agents(
                        (outletDto.getAgents() != null && !outletDto.getAgents().isEmpty())
                                ? outletDto.getAgents().stream()
                                        .map(User::fromDto)
                                        .collect(java.util.stream.Collectors.toSet())
                                : new HashSet<>())
                .customerServicePersonnel(
                        (outletDto.getCustomerServicePersonnel() != null
                                        && !outletDto.getCustomerServicePersonnel().isEmpty())
                                ? outletDto.getCustomerServicePersonnel().stream()
                                        .map(User::fromDto)
                                        .collect(java.util.stream.Collectors.toSet())
                                : new HashSet<>())
                .build();
    }
}
