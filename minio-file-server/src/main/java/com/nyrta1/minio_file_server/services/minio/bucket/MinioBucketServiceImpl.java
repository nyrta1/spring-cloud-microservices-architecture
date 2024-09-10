package com.nyrta1.minio_file_server.services.minio.bucket;

import com.nyrta1.minio_file_server.dto.MinioBucketDTO;
import com.nyrta1.minio_file_server.enums.MinioAccessPermission;
import com.nyrta1.minio_file_server.enums.MinioBucketType;
import com.nyrta1.minio_file_server.exception.MinioBucketException;
import com.nyrta1.minio_file_server.model.MinioBucketEntity;
import com.nyrta1.minio_file_server.repository.MinioBucketRepository;
import com.nyrta1.minio_file_server.services.user.UserService;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioBucketServiceImpl implements MinioBucketService {
    private final MinioBucketRepository bucketRepository;
    private final UserService userService;
    private final MinioClient minioClient;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    @Cacheable(value = "buckets", key = "#bucketId")
    public MinioBucketDTO getBucketDetailsById(UUID bucketId) {
        MinioBucketEntity bucketEntity = bucketRepository.findByIdOrThrow(bucketId);
        return modelMapper.map(bucketEntity, MinioBucketDTO.class);
    }

    @Override
    public List<MinioBucketDTO> getAllBucketDetails() {
        List<MinioBucketEntity> buckets = bucketRepository.findAll();
        return buckets.stream()
                .map(bucket -> modelMapper.map(bucket, MinioBucketDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut(value = "buckets", key = "#result.id")
    public MinioBucketDTO createBucket(String bucketName, MinioAccessPermission permission) {
        try {
            // Retrieve the ID of the authenticated user
            UUID authenticatedUserID = userService.getUserID();

            // Create a new bucket entity
            MinioBucketEntity bucketEntity = MinioBucketEntity.builder()
                    .bucketType(MinioBucketType.PRIVATE)
                    .bucketName(bucketName)
                    .accessType(permission)
                    .ownerUserId(authenticatedUserID)
                    .build();

            // Save the bucket entity to the repository
            MinioBucketEntity flushedEntity = bucketRepository.saveAndFlush(bucketEntity);

            // Prepare the minio args object
            MakeBucketArgs bucketArgs = MakeBucketArgs.builder().bucket(flushedEntity.getId().toString()).build();

            // Create the bucket in Minio
            minioClient.makeBucket(bucketArgs);

            return modelMapper.map(bucketEntity, MinioBucketDTO.class);
        } catch (Exception e) {
            throw new MinioBucketException(
                    String.format("Failed to create bucket with name '%s'. Error: '%s'", bucketName, e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "buckets", key = "#bucketId")
    public void deleteBucketById(UUID bucketId) {
        try {
            // Check if the bucket exists before attempting to delete
            MinioBucketEntity bucketEntity = bucketRepository.findByIdOrThrow(bucketId);

            // Prepare the remove bucket args object
            RemoveBucketArgs minioBucketArgs = RemoveBucketArgs.builder()
                    .bucket(bucketEntity.getBucketName())
                    .build();

            // Delete the bucket from Minio
            minioClient.removeBucket(minioBucketArgs);

            // Remove the bucket entity from the repository
            bucketRepository.delete(bucketEntity);
        } catch (Exception e) {
            throw new MinioBucketException(
                    String.format("Failed to delete bucket with id '%s'. Error: '%s'", bucketId, e.getMessage())
            );
        }
    }
}
