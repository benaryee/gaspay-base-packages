package com.rancard.auth.controller.advise;



import com.rancard.auth.exception.AuthException;
import com.rancard.auth.exception.ServiceException;
import com.rancard.auth.model.enums.ResponseMessage;
import com.rancard.auth.model.response.response.ApiResponse;
import com.rancard.auth.model.response.response.BaseError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NonUniqueResultException;
import javax.persistence.RollbackException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(value = {AuthException.class})
    public final ResponseEntity<?> handleDeliveryException(AuthException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.OK;

        BaseError baseError = new BaseError();
        baseError.setUrl(getUrl(request));
        baseError.setErrorCode(ex.getCode());
        baseError.setErrorMessage(ex.getMessage());

        ApiResponse<?> errorResponse = new ApiResponse<>();
        errorResponse.setCode(100);
        errorResponse.setMessage("failed");
        errorResponse.setError(baseError);

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }


//    @ExceptionHandler(value = InvalidTokenException.class)
//    public final ResponseEntity<Object> handleInvalidTokenException(AuthException ex, WebRequest request) {
//        HttpHeaders headers = new HttpHeaders();
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//
//        BaseError baseError = new BaseError();
//        baseError.setUrl(getUrl(request));
//        baseError.setErrorCode(ex.getCode());
//        baseError.setErrorMessage(ex.getMessage());
//
//        InvalidTokenException e400 = new InvalidTokenException(ex.getMessage()) {
//            @Override
//            public int getHttpErrorCode() {
//                return 400;
//            }
//        };
//
//        return handleExceptionInternal(ex, e400, headers, status, request);
//    }
//

    @ExceptionHandler(value = ServiceException.class)
    public final ResponseEntity<?> handleServiceException(ServiceException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.OK;

        BaseError baseError = new BaseError();
        baseError.setUrl(getUrl(request));
        baseError.setErrorCode(ex.getCode());
        baseError.setErrorMessage(ex.getMessage());

        ApiResponse<Object> errorResponse = new ApiResponse<>();
        errorResponse.setCode(ResponseMessage.FAILED.getCode());
        errorResponse.setMessage(ResponseMessage.FAILED.getMessage());
        errorResponse.setError(baseError);
        errorResponse.setRequestId(request.getSessionId());

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public final ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.CONFLICT;

        BaseError baseError = new BaseError();
        baseError.setUrl(getUrl(request));
        baseError.setErrorCode(HttpStatus.CONFLICT.value());
        baseError.setErrorMessage(ex.getMessage());

        return handleExceptionInternal(ex, baseError, headers, status, request);
    }

    @ExceptionHandler(value = IncorrectResultSizeDataAccessException.class)
    public final ResponseEntity<?> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException ex, WebRequest request) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        BaseError baseError = new BaseError();
        baseError.setUrl(getUrl(request));
        baseError.setErrorCode(status.value());
        baseError.setErrorMessage("Incorrect result size returned");

        return handleExceptionInternal(ex, baseError, headers, status, request);
    }

    @ExceptionHandler(value = NonUniqueResultException.class)
    public final ResponseEntity<?> handleNonUniqueResultException(NonUniqueResultException ex, WebRequest request) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        BaseError baseError = new BaseError();
        baseError.setUrl(getUrl(request));
        baseError.setErrorCode(status.value());
        baseError.setErrorMessage("Result not unique. Unique result expected.");

        return handleExceptionInternal(ex, baseError, headers, status, request);
    }

    @ExceptionHandler(value = RollbackException.class)
    public final ResponseEntity<?> handleRollbackException(RollbackException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex.getCause() instanceof ConstraintViolationException){
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
            return handleConstraintException((ConstraintViolationException) ex.getCause(), headers, status, request);
        }
        else {
            if (logger.isWarnEnabled()) {
                logger.warn("Unknown exception type: " + ex.getClass().getName());
            }
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, "Unknown exception type", headers, status, request);
        }
    }

    @ExceptionHandler(value = InvalidDataAccessApiUsageException.class)
    public final ResponseEntity<?> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        BaseError baseError = new BaseError();
        baseError.setUrl(getUrl(request));
        baseError.setErrorCode(status.value());
        baseError.setErrorMessage(ex.getMostSpecificCause().getMessage());

        ApiResponse<BaseError> errorResponse = new ApiResponse<>();
        errorResponse.setCode(100);
        errorResponse.setMessage("failed");
        errorResponse.setError(baseError);

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        BaseError baseError = new BaseError();
        baseError.setUrl(getUrl(request));
        baseError.setErrorCode(status.value());
        baseError.setErrorMessage("invalid arguments for " + errors);

        ApiResponse<String> errorResponse = new ApiResponse<>();
        errorResponse.setCode(100);
        errorResponse.setMessage("failed");
        errorResponse.setError(baseError);

        return handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    private ResponseEntity<?> handleConstraintException(ConstraintViolationException ex, HttpHeaders headers, HttpStatus
            status, WebRequest webRequest) {

        BaseError baseError = new BaseError();
        baseError.setUrl(getUrl(webRequest));
        baseError.setErrorCode(HttpStatus.CONFLICT.value());
        baseError.setErrorMessage(ex.getMessage());

        return handleExceptionInternal(ex, baseError, headers, status, webRequest);
    }

    private String getUrl(WebRequest webRequest){
        if (webRequest instanceof ServletWebRequest) {
            ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
            HttpServletRequest request = servletRequest.getNativeRequest(HttpServletRequest.class);
            return request != null ? request.getRequestURI() : "unknown url";
        }
        return null;
    }
}
