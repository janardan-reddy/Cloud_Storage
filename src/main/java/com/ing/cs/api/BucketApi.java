package com.ing.cs.api;


import com.ing.cs.api.model.CloudBucketModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/buckets")
public class BucketApi {

    @GetMapping("")
    public ResponseEntity<List<CloudBucketModel>> getAllBuckets() {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping("/{bucketName}")
    public ResponseEntity<CloudBucketModel> addBucket(@PathVariable String bucketName){
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{bucketName}")
    public ResponseEntity<CloudBucketModel> deleteBucket(@PathVariable String bucketName){
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{bucketName}")
    public ResponseEntity<CloudBucketModel> emptyBucket(@PathVariable String bucketName){
        return ResponseEntity.notFound().build();
    }
}
