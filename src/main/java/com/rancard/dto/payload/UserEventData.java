/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import com.rancard.enums.Action;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEventData {
    private String message;
    private Action status;
    private UserDto userDto;
}
