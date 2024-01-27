/*(C) Gaspay App 2024 */
package com.rancard.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TRANSACTION_STATUS {
    REDEEMED("Redeemed"),
    PENDING("Pending"),
    PENDING_APPROVAL("Pending Approval"),
    LATER("Later Redeemed"),
    WIN_IGNORE("Win Ignore"),
    FAILED("Failed"),
    FAIL("Failed"),

    BLOCKED("Blocked"),
    STAFF("Staff"),
    SUSPENDED("Suspended"),
    MILESTONE("Milestone"),
    POSTPAID("Postpaid"),
    TRY_AGAIN("Try Again"),
    INACTIVE("Inactive");

    private final String status;

    TRANSACTION_STATUS(String status) {
        this.status = status;
    }
}
