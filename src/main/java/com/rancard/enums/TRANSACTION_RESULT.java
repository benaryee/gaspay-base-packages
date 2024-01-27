/*(C) Gaspay App 2024 */
package com.rancard.enums;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum TRANSACTION_RESULT {
    WIN("Win"),
    FAIL("Fail"),
    IGNORED("Ignore"),
    IGNORE("Ignore"),
    INVALID("Invalid"),
    USED("Used"),
    STAFF("Staff"),
    FRAUD("Fraud"),
    RETRY("Retry"),
    NOT_ACTIVE("Not_Active");

    private final String result;

    TRANSACTION_RESULT(String result) {
        this.result = result;
    }
}
