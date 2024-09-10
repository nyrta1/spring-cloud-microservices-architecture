package com.nyrta1.minio_file_server.services.minio.object;

import com.nyrta1.minio_file_server.dto.MinioObjectDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MinioObjectService {
    List<MinioObjectDTO> getObjectDetailsById(UUID bucketId);

    MinioObjectDTO uploadById(UUID bucketId, MultipartFile file);

    void deleteById(UUID bucketId, String fileName);
}
