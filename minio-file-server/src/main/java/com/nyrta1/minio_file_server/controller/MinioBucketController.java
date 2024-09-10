package com.nyrta1.minio_file_server.controller;

import com.nyrta1.minio_file_server.dto.MinioBucketDTO;
import com.nyrta1.minio_file_server.enums.MinioAccessPermission;
import com.nyrta1.minio_file_server.services.minio.bucket.MinioBucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/buckets")
@RequiredArgsConstructor
public class MinioBucketController {
    private final MinioBucketService bucketService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_MINIO_ADMIN_ACCESS')")
    public ResponseEntity<List<MinioBucketDTO>> getBucketsList() {
        List<MinioBucketDTO> allBucketDetails = bucketService.getAllBucketDetails();
        return ResponseEntity.ok(allBucketDetails);
    }

    @GetMapping("/{bucketId}")
    @PreAuthorize("hasAuthority('ROLE_MINIO_ADMIN_ACCESS')")
    public ResponseEntity<MinioBucketDTO> getBucket(
            @PathVariable UUID bucketId
    ) {
        MinioBucketDTO bucketDTO = bucketService.getBucketDetailsById(bucketId);
        return ResponseEntity.ok(bucketDTO);
    }

    @PostMapping("/create/{bucketName}/{permission}")
    @PreAuthorize("hasAuthority('ROLE_MINIO_ADMIN_ACCESS')")
    public ResponseEntity<MinioBucketDTO> createBucket(
            @PathVariable String bucketName,
            @PathVariable MinioAccessPermission permission
    ) {
        MinioBucketDTO bucketMetadata = bucketService.createBucket(bucketName, permission);
        return ResponseEntity.ok(bucketMetadata);
    }

    @DeleteMapping("/delete/{bucketId}")
    @PreAuthorize("hasAuthority('ROLE_MINIO_ADMIN_ACCESS')")
    public ResponseEntity<Void> deleteBucket(
            @PathVariable UUID bucketId
    ) {
        bucketService.deleteBucketById(bucketId);
        return ResponseEntity.noContent().build();
    }
}
