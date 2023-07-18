package com.ing.cs.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MinIOBasedStorage {
    
    public void createBucket(CloudBucket bucket) {

    }

    
    public void deleteBucket(String bucket) {

    }

    
    public void emptyBucket(String bucket) {

    }

    
    public List<CloudBucket> listBuckets() {
        return null;
    }

    
    public List<String> listObjects(String bucket) {
        return null;
    }

    
    public void createObject(String bucket, CloudObject cloudObject) {

    }

    
    public void deleteObject(String bucket, String name) {

    }

    
    public CloudObject readObject(String bucket, String name) {
        return null;
    }
}
