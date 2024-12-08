```sh
docker build -t service-registry:latest ./service-registry
docker run -p 8761:8761 --name service-registry-container service-registry:latest

docker build -t product-service:latest ./product-service
docker run -p 8050:8050 --name product-service-container product-service:latest

docker build -t inventory-service:latest ./inventory-service
docker run -p 8060:8060 --name inventory-service-container inventory-service:latest

docker build -t api-gateway:latest ./api-gateway
docker run -p 8080:8080 --name api-gateway-container api-gateway:latest
```
