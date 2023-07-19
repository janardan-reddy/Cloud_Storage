package com.ing.cs.exception;

public class ObjectDoesNotExists extends CloudStorageException {

    public ObjectDoesNotExists(String message) {
        super(message);
    }
}
