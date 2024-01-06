/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import com.rancard.dto.payload.UserDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferMoneyDto implements Serializable {
    private UserDto sender;
    private UserDto recipient;
    private BigDecimal amount;
    private String sessionId;
}
