package com.nyrta1.minio_file_server.repository;

import com.nyrta1.minio_file_server.exception.MinioObjectNotFoundException;
import com.nyrta1.minio_file_server.model.MinioBucketEntity;
import com.nyrta1.minio_file_server.model.MinioObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MinioObjectRepository
        extends JpaRepository<MinioObjectEntity, UUID> {
    Optional<MinioObjectEntity> findByBucketAndFileName(MinioBucketEntity bucketEntity, String fileName);

    default MinioObjectEntity findByBucketAndFileNameOrThrow(MinioBucketEntity bucketEntity, String fileName) {
        return findByBucketAndFileName(bucketEntity, fileName)
                .orElseThrow(() -> new MinioObjectNotFoundException(
                        String.format("The specified object with file name '%s' in bucket '%s' could not be found.", fileName, bucketEntity.getId())
                ));
    }
}
