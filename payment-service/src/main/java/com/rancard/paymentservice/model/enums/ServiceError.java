package com.rancard.paymentservice.model.enums;

public enum ServiceError {
    WALLET_NOT_FOUND(1001, "Wallet not found"),
    WALLET_ALREADY_EXISTS(1002, "Wallet already exists for user"),
    USER_EMAIL_ALREADY_EXISTS(1003, "User email already exists"),
    PASSWORD_MISMATCH(1004, "User passwords don't match"),
    PAYMENT_FAILED(4001,"Failed to process payment");
    private final int code;
    private final String message;
    ServiceError(int code , String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return this.code;
    }

    public String getMessage() {
        return message;
    }
}
