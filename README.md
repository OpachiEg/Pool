## Build


```bash
mvn clean install -DskipTests -Dmaven.test.skip
```

## Run docker database container
```bash
docker-compose up -d postgres
```

## Run all containers
```bash
docker-compose up -d
```

## Swagger
http://localhost:8080/swagger-ui/index.html