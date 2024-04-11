/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.TargetDefinition;
import com.rancard.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignTarget {
    private TargetType targetType;
    private TargetDefinition targetDefinition;
    private Object target;
}
