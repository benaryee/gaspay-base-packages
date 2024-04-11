/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.rancard.enums.Action;
import com.rancard.mongo.Campaign;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignEventData {
    private String message;
    private Action action;
    private PromoTransactionDto promoTransactionDto;
    private Campaign campaign;
}
