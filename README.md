Running the dev environment:

```
mvn spring-boot:run
```

Building docker image:
```
mvn clean package
docker build -t squix78/displaytools:1.0.4 .
```

Building docker on ARM for x86

`docker buildx build --platform linux/amd64 -t thingpulse/esp-iot-flasher:1.0.4 .`

Running docker image:
```
docker run -p 8880:8080 squix78/displaytools:1.0.4
```


