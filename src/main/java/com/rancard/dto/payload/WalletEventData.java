/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.rancard.enums.Action;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletEventData {
    private String message;
    private Action action;
    private WalletDto walletDto;
}
