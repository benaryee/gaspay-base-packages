/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.CampaignTargetDefinition;
import com.rancard.enums.CampaignTargetType;
import java.util.List;
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
    private CampaignTargetType targetType;
    private CampaignTargetDefinition targetDefinition;
    private Object target;
}
