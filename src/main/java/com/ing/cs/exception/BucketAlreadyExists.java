package com.ing.cs.exception;

public class BucketAlreadyExists extends CloudStorageException {

    public BucketAlreadyExists(String message) {
        super(message);
    }
}
