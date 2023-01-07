FROM openjdk:17-jdk-alpine
RUN apk add --no-cache fontconfig ttf-dejavu
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]