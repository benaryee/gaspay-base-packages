/*(C) Gaspay App 2023 */
package com.rancard.enums;

public enum ServiceError {

    // 1XXX - User Exceptions
    USER_ALREADY_EXISTS(1002, "User already exists"),
    USER_NOT_WHITELISTED(1003, "User is not whitelisted"),
    USER_NOT_FOUND(1004, "User not found"),

    // 2XXX - Wallet Exceptions
    WALLET_CREATION_EXCEPTION(2001, "Could not create wallet"),
    INSUFFICIENT_BALANCE(2002, "Insufficient Balance in Wallet"),
    WALLET_NOT_FOUND(2004, "Could not create wallet"),

    // 3XXX - Outlet Exceptions,
    OUTLET_NOT_FOUND(3004, "Outlet not found"),

    // 4XXX - Payment Exceptions,
    PAYMENT_FAILED(4001, "Payment Failed"),
    PAYMENT_NOT_FOUND(4004, "No payment found with given ID"),

    // 5XXX - Address Exceptions
    ADDRESS_NOT_FOUND_EXCEPTION(4004, "Address not found");

    public final int code;

    public final String message;

    ServiceError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
