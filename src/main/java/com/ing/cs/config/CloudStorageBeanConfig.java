package com.ing.cs.config;

import com.ing.cs.exception.MissingConfigException;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class CloudStorageBeanConfig {

    private final Environment environment;

    @Autowired
    public CloudStorageBeanConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(getProperty("minio.endpoint"))
                .credentials(getProperty("minio.accessKey"), getProperty("minio.secretKey"))
                .build();
    }

    private String getProperty(String key) {
        String value = environment.getProperty(key);
        if (value == null) {
            throw new MissingConfigException("missing property " + key);
        }
        return value;
    }
}
