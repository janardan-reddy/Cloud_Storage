# Cloud Storage Api

### Development

#### Start Server

```shell
./mvnw spring-boot:run
```


#### Create Image using Spring 

```shell
./mvnw spring-boot:build-image
```

#### Create Image Docker CLI

```shell
./mvnw clean package
docker build -t cloud-storage . 
```

#### Start MinIO Using Docker

````shell
docker run -p 9000:9000 -p 9001:9001 -v minio-data:/data -e MINIO_ROOT_USER=minioadmin -e MINIO_ROOT_PASSWORD=minioadmin quay.io/minio/minio server /data --console-address ":9001"
````

## How?
1. Created Sprint Boot application using [Spring Initializer](https://start.spring.io/)
2. Added Spring Data using JPA and using H2 for any DB (if needed)

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.1/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.1/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.1/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

