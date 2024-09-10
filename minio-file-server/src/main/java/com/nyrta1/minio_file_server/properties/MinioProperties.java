package com.nyrta1.minio_file_server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String url;
    private Access access;

    @Data
    public static class Access {
        private String key;
        private String secret;
    }
}
