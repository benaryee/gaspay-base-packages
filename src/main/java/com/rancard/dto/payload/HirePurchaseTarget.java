/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rancard.enums.TargetDefinition;
import com.rancard.enums.TargetType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
public class HirePurchaseTarget extends CampaignTarget {

}
