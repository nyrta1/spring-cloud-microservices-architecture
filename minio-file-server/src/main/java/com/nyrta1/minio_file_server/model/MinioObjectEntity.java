package com.nyrta1.minio_file_server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.entity.ContentType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity(name = "minio_object_metadata")
@NoArgsConstructor
@AllArgsConstructor
public class MinioObjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false, unique = true)
    private UUID filePath;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private ContentType contentType;

    @Column(nullable = false)
    private UUID ownerUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "bucket_id",
            nullable = false
    )
    private MinioBucketEntity bucket;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
