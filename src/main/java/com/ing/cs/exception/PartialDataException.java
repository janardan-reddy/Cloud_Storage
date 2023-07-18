package com.ing.cs.exception;

public class PartialDataException extends CloudStorageException {

    public PartialDataException(String message) {
        super(message);
    }
    public PartialDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
