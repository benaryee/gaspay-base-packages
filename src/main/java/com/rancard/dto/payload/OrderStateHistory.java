/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.rancard.enums.OrderStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStateHistory {
    private OrderStatus orderStatus;
    private LocalDateTime timestamp;
}
