# Cloud Storage Api

## Status
 - [x] File Upload
 - [x] File Download
 - [x] File Delete
 - [x] List all files
 - [x] Different File types/sizes
 - [x] Error Handling/Status codes
 - [x] Large file upload/downloads
   - Can be improved
 - [ ] Documentation
   - [ ] Swagger partially done, unable get api-docs working
   - [x] Postman is done
 - [x] Instructions
 - [ ] Minikube
   - [x] Docker
   - [ ] Docker compose is not exposing port 8080
   - [ ] Unable to use generated image in Minikube, need more time


## Development

#### 1. Start MinIO Using Docker

```shell
docker run -p 9000:9000 -p 9001:9001 -v minio-data:/data -e MINIO_ROOT_USER=minioadmin -e MINIO_ROOT_PASSWORD=minioadmin quay.io/minio/minio server /data --console-address ":9001"
```


#### 2. Create Jar

```shell
./mvnw clean package
```

#### 3. Create Docker Image

```shell
docker build -t ing/cloud-storage . 
```




#### Start Server

```shell
./mvnw spring-boot:run
```


#### Using MiniKube

```shell
./kube/setup-all.sh
```
