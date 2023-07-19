package com.ing.cs.api;

import com.ing.cs.api.model.CloudObjectModel;
import com.ing.cs.exception.PartialDataException;
import com.ing.cs.services.CloudObject;
import com.ing.cs.services.MinIOBasedStorage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/buckets")
public class ObjectApi {
    private final MinIOBasedStorage minIOBasedStorage;

    public ObjectApi(MinIOBasedStorage minIOBasedStorage){
        this.minIOBasedStorage = minIOBasedStorage;
    }
    @GetMapping("/{bucketName}/objects")
    public ResponseEntity<List<CloudObjectModel>> getAllObjects(@PathVariable String bucketName, @RequestParam(value = "prefix", required = false) String prefix) {
        var result = minIOBasedStorage.listObjects(bucketName, prefix).stream().map(ObjectApi::toApiModel).toList();
        return ResponseEntity.ok(result);
    }
    @PostMapping("/{bucketName}/objects")
    public ResponseEntity<CloudObjectModel> addObject(@PathVariable String bucketName, @RequestParam("object") String objectPath, HttpServletRequest request) {
        try {
            var object = minIOBasedStorage.createObject(bucketName,objectPath, request.getInputStream());
            return ResponseEntity.created(getUri(bucketName, objectPath)).build();
        } catch (IOException e) {
            throw new PartialDataException("failed to read object content", e);
        }
    }

    @DeleteMapping("/{bucketName}/objects")
    public ResponseEntity<CloudObjectModel> deleteObjects(@PathVariable String bucketName, @RequestParam(value = "object", required = false) String object, @RequestParam(value = "prefix", required = false) String prefix ) {
       minIOBasedStorage.deleteObjects(bucketName, object, prefix);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bucketName}/object")
    public ResponseEntity<CloudObjectModel> getObject(@PathVariable String bucketName, @RequestParam(value = "object") String objectPath) {
        var response = minIOBasedStorage.readObject(bucketName, objectPath);
        return ResponseEntity.ok(toApiModel(response));
    }

    private static URI getUri(String bucketName, String objectPath) {
        return UriComponentsBuilder.newInstance()
                .pathSegment("buckets", bucketName, "objects").queryParam("object", objectPath)
                .build()
                .toUri();
    }

    private static CloudObjectModel toApiModel(CloudObject cloudObject) {
        CloudObjectModel model = new CloudObjectModel();
        model.setName(cloudObject.getName());
        model.setPrefix(cloudObject.getPrefix());
        model.setSize(cloudObject.getSize());
        return model;
    }
}
