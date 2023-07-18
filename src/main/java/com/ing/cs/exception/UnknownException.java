package com.ing.cs.exception;

public class UnknownException extends CloudStorageException {

    public UnknownException(String message) {
        super(message);
    }
    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }

}
