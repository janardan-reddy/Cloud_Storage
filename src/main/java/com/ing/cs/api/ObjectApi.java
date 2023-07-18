package com.ing.cs.api;

import com.ing.cs.api.model.CloudObjectModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/objects")
public class ObjectApi {

    @PostMapping("/{bucketName}/{objectPath}")
    public ResponseEntity<CloudObjectModel> addObject(@PathVariable String bucketName, @PathVariable String objectPath, HttpServletRequest request) {
        return ResponseEntity.created(URI.create(request.getRequestURI())).build();
    }

    @DeleteMapping("/{bucketName}/{objectPath}")
    public ResponseEntity<CloudObjectModel> deleteObject(@PathVariable String bucketName, @PathVariable String objectPath) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{bucketName}/{objectPath}")
    public ResponseEntity<CloudObjectModel> getObject(@PathVariable String bucketName, @PathVariable String objectPath) {
        return ResponseEntity.notFound().build();
    }

}
