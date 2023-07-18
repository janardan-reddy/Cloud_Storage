package com.ing.cs.exception;

public class MissingConfigException extends CloudStorageException {

    public MissingConfigException(String message) {
        super(message);
    }
}
