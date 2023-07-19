package com.ing.cs.services;

import com.ing.cs.exception.BucketAlreadyExists;
import com.ing.cs.exception.CloudStorageException;
import com.ing.cs.exception.ObjectDoesNotExists;
import com.ing.cs.exception.UnknownException;
import io.minio.*;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
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
                logger.warn("service -- makeBucket -- bucket " + bucket + " already exists");
                throw new BucketAlreadyExists(bucket + " already exists");
            }
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket.name()).region(bucket.region()).build());
            logger.info("service -- makeBucket -- created bucket " + bucket);
        } catch (CloudStorageException e) {
            throw e;
        } catch (Exception e) {
            logger.warn("unknown error creating bucket", e);
            throw new UnknownException("unknown error creating bucket", e);
        }
    }

    public List<CloudBucket> listBuckets() {
        try {
            var result = minioClient.listBuckets().stream()
                    .map(b -> new CloudBucket(b.name(), null))
                    .toList();
            logger.info("service -- listBuckets -- Buckets found {}", result.size());
            return result;
        } catch (Exception e) {
            logger.warn("service -- listBuckets -- unknown error listing buckets", e);
            throw new UnknownException("unknown error listing buckets", e);
        }
    }


    public List<CloudObject> listObjects(String bucket, String prefix) {
        try {
            ListObjectsArgs lbArgs = ListObjectsArgs.builder()
                    .bucket(bucket)
                    .recursive(true)
                    .prefix(prefix)
                    .build();
            var response = minioClient.listObjects(lbArgs);
            List<CloudObject> result = new ArrayList<>();
            for (Result<Item> itemResult : response) {
                Item i = itemResult.get();
                CloudObject obj = toCloudObject(i.objectName(), i.size());
                result.add(obj);
            }
            logger.info("service -- listObjects -- Objects found ({})", result.size());
            return result;

        } catch (Exception e) {
            logger.warn("service -- listObjects -- Unknown error listing objects", e);
            throw new UnknownException("Unknown error listing objects", e);
        }
    }


    public CloudObject createObject(String bucket, String objectPath, InputStream data) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectPath)
                    .stream(data, -1, 5 * 1024 * 1024)
                    .build();
            var result = minioClient.putObject(putObjectArgs);
            CloudObject response = toCloudObject(result.object(), result.headers().size());
            logger.info("service -- createObject -- Object created {}", response);
            return response;
        } catch (Exception e) {
            logger.warn("service -- createObject -- unknown error creating object", e);
            throw new UnknownException("unknown error creating object", e);
        }
    }

    public void deleteObjects(String bucket, String name, String prefix){
        try{
            if (prefix != null){
                 List<CloudObject> objects = listObjects(bucket, prefix);
                 for( CloudObject obj : objects){
                     deleteObject(bucket,prefix+"/"+obj.getName());
                 }
                 logger.info("service -- deleteObjects -- Objects deleted from prefix {}", prefix);
            }
            else{
                deleteObject(bucket,name);
            }

        }catch(Exception e){
            logger.warn("service -- deleteObjects -- Unknown error, deleting objects from prefix ", e);
            throw new UnknownException("Unknown error, deleting objects from prefix ");
        }
    }


    public CloudObject readObject(String bucket, String name) {
        boolean objectExists = isObjectExists(bucket, name);
        if (objectExists) {
            try {
                GetObjectArgs goArgs = GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(name)
                        .build();

                GetObjectResponse result = minioClient.getObject(goArgs);
                CloudObject response = toCloudObject(result.object(), result.headers().size());
                logger.info("service -- readObject -- Object found {}", response);
                return response;
            } catch (Exception e) {
                logger.warn("service -- readObject -- unknown error reading an object", e);
                throw new UnknownException("Unknown error while reading object", e);
            }
        }
        return null;
    }

    private void deleteObject(String bucket, String name) {
        try {
            RemoveObjectArgs roArgs = RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(name)
                    .build();
            minioClient.removeObject(roArgs);
            logger.info("service -- deleteObject -- object deleted successfully {}", name);
        } catch (Exception e) {
            logger.warn("service -- deleteObject -- unknown error deleting an object", e);
            throw new UnknownException("Unknown error deleting an object", e);
        }
    }

    private boolean isObjectExists(String bucket, String name) {
        try {
            StatObjectArgs soArgs = StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(name)
                    .build();
            var object = minioClient.statObject(soArgs);
            return object.size() > 0;
        } catch (Exception e) {
            logger.warn("service -- isObjectExists -- Object does not exists ", e);
            throw new ObjectDoesNotExists("Object does not exists");
        }
    }

    private CloudObject toCloudObject(String name, long size) {
        String prefix;
        String onlyName;
        int lastSlash = name.lastIndexOf("/");
        if (lastSlash < 0) {
            prefix = "";
            onlyName = name;
        } else {
            prefix = name.substring(0, lastSlash);
            onlyName = name.substring(lastSlash+1);
        }
        return new CloudObject(prefix, onlyName, size);
    }
}
