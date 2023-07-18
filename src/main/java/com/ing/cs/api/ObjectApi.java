package com.ing.cs.api;

import com.ing.cs.api.model.CloudObjectModel;
import com.ing.cs.exception.PartialDataException;
import com.ing.cs.services.CloudObject;
import com.ing.cs.services.MinIOBasedStorage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/buckets")
public class ObjectApi {
    private final MinIOBasedStorage minIOBasedStorage;

    public ObjectApi(MinIOBasedStorage minIOBasedStorage){
        this.minIOBasedStorage = minIOBasedStorage;
    }
    @PostMapping("/{bucketName}/objects")
    public ResponseEntity<CloudObjectModel> addObject(@PathVariable String bucketName, @RequestParam("object") String objectPath, HttpServletRequest request) {
        try {
            var object = minIOBasedStorage.createObject(bucketName,new CloudObject(objectPath, request.getContentType()), request.getInputStream());
            return ResponseEntity.created(getUri(bucketName, objectPath)).build();
        } catch (IOException e) {
            throw new PartialDataException("failed to read object content", e);
        }
    }

    @DeleteMapping("/{bucketName}/objects")
    public ResponseEntity<CloudObjectModel> deleteObject(@PathVariable String bucketName, @RequestParam("object") String objectPath) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{bucketName}/objects")
    public ResponseEntity<CloudObjectModel> getObject(@PathVariable String bucketName, @RequestParam("object") String objectPath) {
        return ResponseEntity.notFound().build();
    }

    private static URI getUri(String bucketName, String objectPath) {
        return UriComponentsBuilder.newInstance()
                .pathSegment("buckets", bucketName, "objects").queryParam("object", objectPath)
                .build()
                .toUri();
    }
}
