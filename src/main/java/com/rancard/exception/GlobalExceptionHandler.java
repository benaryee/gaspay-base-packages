/*(C) Gaspay App 2023 */
package com.rancard.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ServiceExceptionResponse> handleServiceException(ServiceException e) {
        log.error("error occurred {}", e.getMessage());
        log.error("error occurred {}", e);
        ServiceExceptionResponse response = new ServiceExceptionResponse(e.getCode(), e.getMessage());
        return new ResponseEntity<>(response , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestCustomExceptionHandler.class)
    public ResponseEntity<ServiceExceptionResponse> handleBadRequestException(ServiceException e) {
        log.error("error occurred {}", e);
        ServiceExceptionResponse response = new ServiceExceptionResponse(e.getCode(), e.getMessage());
        return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ServiceExceptionResponse> handleUnauthorizedException(UnauthorizedException ex) {
        log.error("error occurred {}", ex);
        ServiceExceptionResponse response = new ServiceExceptionResponse(401, ex.getMessage());
        return new ResponseEntity<>(response , HttpStatus.UNAUTHORIZED);
    }

}
