/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import lombok.Data;

@Data
public class LocationResponse {
    private LocationData data;
    private boolean found;
}
