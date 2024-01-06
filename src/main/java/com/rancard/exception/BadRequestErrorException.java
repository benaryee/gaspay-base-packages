package com.rancard.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestErrorException extends Exception{

    private static final long serialVersionUID = 1L;
    public BadRequestErrorException(String message){
        super(message);
    }
}
