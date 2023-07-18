package com.ing.cs.api;

import com.ing.cs.api.model.ErrorResponse;
import com.ing.cs.exception.BucketAlreadyExists;
import com.ing.cs.exception.CloudStorageException;
import com.ing.cs.exception.MissingConfigException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {BucketAlreadyExists.class})
    protected ResponseEntity<Object> handleBucketAlreadyExists(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, errorResponse("bucket already exists"), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {CloudStorageException.class})
    protected ResponseEntity<Object> handleCloudStorageException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, errorResponse("internal error, retry"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {MissingConfigException.class})
    protected ResponseEntity<Object> handleMissingConfigException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, errorResponse("internal error, retry"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    public static ErrorResponse errorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        return errorResponse;
    }
}