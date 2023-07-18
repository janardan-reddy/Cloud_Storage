package com.ing.cs;

import io.minio.MinioClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CloudStorageApplication {


	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

}
