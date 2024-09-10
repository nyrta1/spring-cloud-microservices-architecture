package com.nyrta1.minio_file_server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MinioObjectDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    private UUID id;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String contentType;
    private String bucketName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
