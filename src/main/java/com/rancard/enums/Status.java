/*(C) Gaspay App 2024 */
package com.rancard.enums;

import lombok.Getter;

@Getter
public enum Status {
    SUCCESS("Success"),
    FAILED("Failed"),
    ERROR("Error");

    private final String value;

    Status(String value) {
        this.value = value;
    }
}
