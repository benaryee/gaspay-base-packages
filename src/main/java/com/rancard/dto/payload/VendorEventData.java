/*(C) Gaspay App 2023-2024 */
package com.rancard.dto.payload;

import com.rancard.enums.Action;
import lombok.*;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class VendorEventData {
    private String message;
    private Action status;
    private VendorDto vendorDto;
}
