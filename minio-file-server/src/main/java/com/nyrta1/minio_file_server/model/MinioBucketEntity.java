package com.nyrta1.minio_file_server.model;

import com.nyrta1.minio_file_server.enums.MinioAccessPermission;
import com.nyrta1.minio_file_server.enums.MinioBucketType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity(name = "minio_bucket_metadata")
@NoArgsConstructor
@AllArgsConstructor
public class MinioBucketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String bucketName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MinioAccessPermission accessType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MinioBucketType bucketType;

    @Column(nullable = false)
    private UUID ownerUserId;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "bucket",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MinioObjectEntity> objects;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
