package com.rancard.ussdapp.model.enums;

public enum ServiceError {

    USER_NOT_FOUND(1001, "User not found"),
    USER_ALREADY_EXISTS(1002, "User already exists"),
    WALLET_CREATION_EXCEPTION(3001, "Could not create wallet"),
    ADDRESS_NOT_FOUND_EXCEPTION(4001, "Address not found");

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
