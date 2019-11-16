# common/tdoer-gateway

- Tag: tdoer/tdoer-gateway:1.0.0
- Base Image: tdoer/alpine-openjdk8-jre-java:1.0.0

## Build

Example version: 1.0.0

```
docker build -t tdoer/tdoer-gateway:1.0.0 .
```

## Build and Push

 Usage: 
 
 ```
 ./build.sh <VERSION>
 ```
 
 Example:
  
 ```
 ./build.sh 1.0.0
 ```

## Run    

```
docker run --name tdoer-gateway -p 7060:7060 -p 7061:7061 -d tdoer/tdoer-gateway:1.0.0
```
