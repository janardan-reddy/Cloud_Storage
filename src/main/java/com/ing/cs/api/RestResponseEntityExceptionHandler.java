package com.ing.cs.api;

import com.ing.cs.api.model.ErrorResponse;
import com.ing.cs.exception.*;
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
    protected ResponseEntity<Object> handleBucketAlreadyExists(BucketAlreadyExists ex, WebRequest request) {
        return handleExceptionInternal(ex, errorResponse("bucket already exists"), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler(value = {ObjectDoesNotExists.class})
    protected ResponseEntity<Object> handleObjectDoesNotExists(ObjectDoesNotExists ex, WebRequest request) {
        return handleExceptionInternal(ex, errorResponse("bucket already exists"), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {CloudStorageException.class})
    protected ResponseEntity<Object> handleCloudStorageException(CloudStorageException ex, WebRequest request) {
        return handleExceptionInternal(ex, errorResponse("internal error, retry"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {MissingConfigException.class})
    protected ResponseEntity<Object> handleMissingConfigException(MissingConfigException ex, WebRequest request) {
        return handleExceptionInternal(ex, errorResponse("internal error, retry"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {PartialDataException.class})
    protected ResponseEntity<Object> handleMissingConfigException(PartialDataException ex, WebRequest request) {
        return handleExceptionInternal(ex, errorResponse("failed to read object content"), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {CloudStorageHttpException.class})
    protected ResponseEntity<Object> handleMissingConfigException(CloudStorageHttpException ex, WebRequest request) {
        return handleExceptionInternal(ex, errorResponse(ex.getMessage()), new HttpHeaders(), ex.getStatusCode(), request);
    }

    public static ErrorResponse errorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        return errorResponse;
    }
}