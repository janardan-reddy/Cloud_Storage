package com.ing.cs.services;

import com.ing.cs.exception.BucketAlreadyExists;
import com.ing.cs.exception.UnknownException;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MinIOBasedStorage {

    private final MinioClient minioClient;

    @Autowired
    public MinIOBasedStorage(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void makeBucket(CloudBucket bucket) {
        try {
            boolean alreadyExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket.name()).build());
            if (alreadyExists) {
                throw new BucketAlreadyExists(bucket + " already exists");
            }
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket.name()).region(bucket.region()).build());
        } catch (Exception e) {
            throw new UnknownException("unknown error creating bucket", e);
        }
    }


    public void deleteBucket(String bucket) {

    }


    public void emptyBucket(String bucket) {

    }


    public List<CloudBucket> listBuckets() {
        try {
            return minioClient.listBuckets().stream()
                    .map(b -> new CloudBucket(b.name(), null))
                    .toList();
        } catch (Exception e) {
            throw new UnknownException("unknown error listing buckets", e);
        }
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
