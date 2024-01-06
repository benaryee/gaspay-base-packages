package com.rancard.exception;
import com.rancard.enums.ServiceError;

public class BadRequestCustomExceptionHandler extends RuntimeException {

    private int code;
    private String message;

    public BadRequestCustomExceptionHandler(int code, String message){
        this.code = code;
        this.message = message;
    }

    public BadRequestCustomExceptionHandler(ServiceError error){
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
