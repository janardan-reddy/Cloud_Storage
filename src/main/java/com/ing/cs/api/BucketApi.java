package com.ing.cs.api;


import com.ing.cs.api.model.CloudBucketModel;
import com.ing.cs.services.CloudBucket;
import com.ing.cs.services.MinIOBasedStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/buckets")
public class BucketApi {

    private final MinIOBasedStorage minIOBasedStorage;

    @Autowired
    public BucketApi(MinIOBasedStorage minIOBasedStorage) {
        this.minIOBasedStorage = minIOBasedStorage;
    }

    @GetMapping
    public ResponseEntity<List<CloudBucketModel>> getAllBuckets() {
        var buckets = minIOBasedStorage.listBuckets().stream().map(BucketApi::toApiModel).toList();
        return ResponseEntity.ok(buckets);
    }

    @PostMapping("/{bucketName}")
    public ResponseEntity<CloudBucketModel> addBucket(@PathVariable String bucketName) {
        var bucket = new CloudBucket(bucketName, null);
        minIOBasedStorage.makeBucket(bucket);
        return ResponseEntity.ok(toApiModel(bucket));
    }

    @DeleteMapping("/{bucketName}")
    public ResponseEntity<CloudBucketModel> deleteBucket(@PathVariable String bucketName) {
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{bucketName}")
    public ResponseEntity<CloudBucketModel> emptyBucket(@PathVariable String bucketName) {
        return ResponseEntity.notFound().build();
    }

    private static CloudBucketModel toApiModel(CloudBucket cloudBucket)  {
        CloudBucketModel model = new CloudBucketModel();
        model.setName(cloudBucket.name());
        model.setRegion(cloudBucket.region());
        return model;
    }
}
