package com.rancard.auth.model.payload;

import lombok.Data;

@Data
public class LocationResponse {
    private LocationData data;
    private boolean found;
}
