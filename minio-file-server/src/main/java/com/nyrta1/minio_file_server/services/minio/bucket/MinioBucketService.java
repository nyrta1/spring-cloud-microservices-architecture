package com.nyrta1.minio_file_server.services.minio.bucket;

import com.nyrta1.minio_file_server.dto.MinioBucketDTO;
import com.nyrta1.minio_file_server.enums.MinioAccessPermission;

import java.util.List;
import java.util.UUID;

public interface MinioBucketService {
    MinioBucketDTO getBucketDetailsById(UUID bucketId);

    List<MinioBucketDTO> getAllBucketDetails();

    MinioBucketDTO createBucket(String bucketName, MinioAccessPermission permission);

    void deleteBucketById(UUID bucketId);

}
