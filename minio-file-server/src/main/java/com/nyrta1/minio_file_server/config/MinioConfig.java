package com.nyrta1.minio_file_server.config;

import com.nyrta1.minio_file_server.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {
    private final MinioProperties minioProperties;

    @Bean
    @Primary
    public MinioClient minioClient() {
        return new MinioClient.Builder()
                .credentials(
                        minioProperties.getAccess().getKey(),
                        minioProperties.getAccess().getSecret()
                )
                .endpoint(
                        minioProperties.getUrl()
                )
                .build();
    }
}
