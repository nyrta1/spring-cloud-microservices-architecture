package com.nyrta1.minio_file_server.repository;

import com.nyrta1.minio_file_server.exception.MinioBucketNotFoundException;
import com.nyrta1.minio_file_server.model.MinioBucketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MinioBucketRepository
        extends JpaRepository<MinioBucketEntity, UUID> {
    default MinioBucketEntity findByIdOrThrow(UUID bucketId) {
        return findById(bucketId)
                .orElseThrow(() -> new MinioBucketNotFoundException(
                        String.format("The specified bucket with id '%s' could not be found.", bucketId)
                ));
    }
}
