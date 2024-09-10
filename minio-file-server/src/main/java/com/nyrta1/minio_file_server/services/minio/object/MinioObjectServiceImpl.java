package com.nyrta1.minio_file_server.services.minio.object;

import com.nyrta1.minio_file_server.dto.MinioObjectDTO;
import com.nyrta1.minio_file_server.model.MinioBucketEntity;
import com.nyrta1.minio_file_server.model.MinioObjectEntity;
import com.nyrta1.minio_file_server.repository.MinioBucketRepository;
import com.nyrta1.minio_file_server.repository.MinioObjectRepository;
import com.nyrta1.minio_file_server.services.user.UserService;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MinioObjectServiceImpl implements MinioObjectService {
    private final MinioBucketRepository bucketRepository;
    private final MinioObjectRepository objectRepository;
    private final UserService userService;
    private final MinioClient minioClient;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<MinioObjectDTO> getObjectDetailsById(UUID bucketId) {
        // Retrieve the bucket entity from the bucket name
        MinioBucketEntity bucketEntity = bucketRepository.findByIdOrThrow(bucketId);

        // Return the result
        return bucketEntity.getObjects().stream()
                .map(object -> modelMapper.map(object, MinioObjectDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MinioObjectDTO uploadById(UUID bucketId, MultipartFile file) {
        try {
            // Validation
            Objects.requireNonNull(file);

            // Retrieve the bucket entity from the bucket name
            MinioBucketEntity bucketEntity = bucketRepository.findByIdOrThrow(bucketId);

            // Create an entity representing the file to be uploaded
            UUID authenticatedUserID = userService.getUserID();
            UUID randomUUID = UUID.randomUUID();
            MinioObjectEntity minioObject = MinioObjectEntity.builder()
                    .fileName(file.getOriginalFilename())
                    .filePath(randomUUID)
                    .fileSize(file.getSize())
                    .ownerUserId(authenticatedUserID)
                    .bucket(bucketEntity)
                    .contentType(ContentType.parse(Objects.requireNonNull(file.getContentType())))
                    .build();

            // Get the input stream of the file
            InputStream fileInputStream = file.getInputStream();

            // Build the arguments for uploading the file to Minio
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketEntity.getBucketName())
                    .object(minioObject.getFilePath().toString())
                    .stream(fileInputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();

            // Upload the file to Minio
            minioClient.putObject(args);

            // Save the file record to the database
            objectRepository.save(minioObject);

            // Return the result
            return modelMapper.map(minioObject, MinioObjectDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download object", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(UUID bucketId, String fileName) {
        try {
            // Find the bucket by name
            MinioBucketEntity bucketEntity = bucketRepository.findByIdOrThrow(bucketId);

            // Get the file entity or throw an exception if not found
            MinioObjectEntity objectEntity = objectRepository.findByBucketAndFileNameOrThrow(bucketEntity, fileName);

            // Delete the file from MinIO
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                    .bucket(bucketEntity.getBucketName())
                    .object(fileName)
                    .build();
            minioClient.removeObject(args);

            // Delete the file record from the database
            objectRepository.delete(objectEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download object", e);
        }
    }
}
