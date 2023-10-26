package com.rancard.paymentservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ServiceExceptionResponse> handleServiceException(ServiceException e) {
        log.error("error occurred {}", e);
        ServiceExceptionResponse response = new ServiceExceptionResponse(e.getCode(), e.getMessage());
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

}
