package com.nyrta1.minio_file_server;

import com.nyrta1.minio_file_server.properties.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({
		MinioProperties.class
})
public class MinioFileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinioFileServiceApplication.class, args);
	}

}
