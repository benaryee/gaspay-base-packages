/*(C) Gaspay App 2023 */
package com.rancard.dto.request;

import com.rancard.dto.payload.UserDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopupRequestDto implements Serializable {
    private UserDto user;
    private BigDecimal amount;
    private String channel;
    private String mobileNetwork;
    private String sessionId;
    private String transactionId;
}
