package com.ing.cs.exception;

import org.springframework.http.HttpStatusCode;

public class CloudStorageHttpException extends RuntimeException {

    private HttpStatusCode statusCode;

    private String message;

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static CloudStorageHttpException forStatus(HttpStatusCode statusCode, String message) {
        CloudStorageHttpException exception = new CloudStorageHttpException();
        exception.setStatusCode(statusCode);
        exception.setMessage(message);
        return exception;
    }
}
