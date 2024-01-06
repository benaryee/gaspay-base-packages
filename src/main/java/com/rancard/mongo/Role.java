/*(C) Gaspay App 2023 */
package com.rancard.mongo;

import com.rancard.dto.payload.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private int code;

    private String name;

    private String description;

    @CreatedDate private DateTime _created;

    @LastModifiedDate private DateTime _modified;

    @CreatedBy private String createdBy;

    public RoleDto toDto() {
        return RoleDto.builder().code(code).name(name).description(description).build();
    }

    public static Role fromDto(RoleDto roleDto) {
        return Role.builder()
                .code(roleDto.getCode())
                .name(roleDto.getName())
                .description(roleDto.getDescription())
                .build();
    }
}
