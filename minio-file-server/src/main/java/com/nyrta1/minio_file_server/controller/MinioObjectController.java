package com.nyrta1.minio_file_server.controller;

import com.nyrta1.minio_file_server.annotation.minio.MinioPermission;
import com.nyrta1.minio_file_server.dto.MinioObjectDTO;
import com.nyrta1.minio_file_server.enums.MinioAccessPermission;
import com.nyrta1.minio_file_server.services.minio.object.MinioObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/objects")
@RequiredArgsConstructor
public class MinioObjectController {
    private final MinioObjectService minioService;

    // TODO: 1. Add pagination logic Page<MinioObjectDTO>
    @GetMapping("/{bucketId}")
    @MinioPermission(permission = MinioAccessPermission.READ)
    public ResponseEntity<List<MinioObjectDTO>> list(
            @PathVariable UUID bucketId
    ) {
        List<MinioObjectDTO> files = minioService.getObjectDetailsById(bucketId);
        return ResponseEntity.ok(files);
    }


    @PostMapping("/upload/{bucketId}")
    @MinioPermission(permission = MinioAccessPermission.WRITE)
    public ResponseEntity<MinioObjectDTO> upload(
            @PathVariable UUID bucketId,
            @RequestParam("file") MultipartFile file
    ) {
        MinioObjectDTO uploadMinioObject = minioService.uploadById(bucketId, file);
        return ResponseEntity.ok(uploadMinioObject);
    }


    @DeleteMapping("/remove/{bucketId}/{fileName}")
    @MinioPermission(permission = MinioAccessPermission.DELETE)
    public ResponseEntity<Void> remove(
            @PathVariable UUID bucketId,
            @PathVariable String fileName
    ) {
        minioService.deleteById(bucketId, fileName);
        return ResponseEntity.noContent().build();
    }
}
