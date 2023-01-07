Running the dev environment:

```
mvn spring-boot:run
```

Building docker image:
```
mvn clean package
docker build -t squix78/displaytools:latest .
```

Running docker image:
```
docker run -p 8880:8080 squix78/displaytools:latest
```


