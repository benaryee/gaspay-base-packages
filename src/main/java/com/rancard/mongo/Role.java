/*(C) Gaspay App 2023-2024 */
package com.rancard.mongo;

import com.rancard.dto.payload.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private int code;

    private String name;

    public RoleDto toDto() {
        return RoleDto.builder().code(code).name(name).build();
    }

    public static Role fromDto(RoleDto roleDto) {
        return Role.builder().code(roleDto.getCode()).name(roleDto.getName()).build();
    }
}
