FROM gradle:jdk17-alpine
WORKDIR /human-query-service

COPY src /human-query-service/src/
COPY build.gradle .
COPY lombok.config .

RUN gradle clean build -x test

ENTRYPOINT ["java", "-jar", "./build/libs/human-query-service-1.0.0.jar"]