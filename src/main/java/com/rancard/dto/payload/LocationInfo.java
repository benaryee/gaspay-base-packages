/*(C) Gaspay App 2023 */
package com.rancard.dto.payload;

import lombok.Data;

@Data
public class LocationInfo {
    private String Street;
    private String District;
    private String Region;
    private String PostCode;
    private String GPSName;
    private String CenterLongitude;
    private String CenterLatitude;
}
