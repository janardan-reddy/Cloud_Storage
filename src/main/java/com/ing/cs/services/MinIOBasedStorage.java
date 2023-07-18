package com.ing.cs.services;

import com.ing.cs.exception.BucketAlreadyExists;
import com.ing.cs.exception.CloudStorageException;
import com.ing.cs.exception.UnknownException;
import io.minio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;


@Service
public class MinIOBasedStorage {

    private final static Logger logger = LoggerFactory.getLogger(MinIOBasedStorage.class);

    private final MinioClient minioClient;

    @Autowired
    public MinIOBasedStorage(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void makeBucket(CloudBucket bucket) {
        try {
            boolean alreadyExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket.name()).build());
            if (alreadyExists) {
                logger.info("bucket " + bucket + " already exists");
                throw new BucketAlreadyExists(bucket + " already exists");
            }
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket.name()).region(bucket.region()).build());
        } catch (CloudStorageException e) {
            throw e;
        } catch (Exception e) {
            logger.warn("unknown error creating bucket", e);
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


    public List<CloudObject> listObjects(String bucket) {
        return null;
    }


    public CloudObject createObject(String bucket, CloudObject cloudObject, InputStream data) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(cloudObject.name())
                    .contentType(cloudObject.contentType())
                    .stream(data, -1, 5 * 1024 * 1024)
                    .build();
            minioClient.putObject(putObjectArgs);
            return cloudObject;
        } catch (Exception e) {
            throw new UnknownException("unknown error creating object", e);
        }
    }


    public void deleteObject(String bucket, String name) {

    }


    public CloudObject readObject(String bucket, String name) {
        return null;
    }
}
