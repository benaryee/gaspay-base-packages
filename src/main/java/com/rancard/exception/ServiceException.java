/*(C) Gaspay App 2023 */
package com.rancard.exception;

import com.rancard.enums.ServiceError;
import lombok.Data;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

    private int code;
    private String message;
    private HttpStatus httpStatus;

    public ServiceException(int code, String message){
        this.code = code;
        this.message = message;
    }

    public ServiceException(ServiceError error){
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public ServiceException(ServiceError error, HttpStatus httpStatus){
        this.code = error.getCode();
        this.message = error.getMessage();
        this.httpStatus = httpStatus;
    }

    public ServiceException(int code, String message, HttpStatus httpStatus){
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
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

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
