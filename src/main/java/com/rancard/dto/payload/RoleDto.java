/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto implements Serializable {

    private int code;

    private String name;
    private String description;
    private String createdBy;
}
