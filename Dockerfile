FROM eclipse-temurin:25-jdk-alpine AS builder
WORKDIR /build

COPY gradlew .
COPY gradle gradle
COPY gradle.properties build.gradle gradle.properties ./

RUN ./gradlew dependencies  --no-daemon -x test
COPY src src
RUN ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:25-jdk-alpine AS extract

WORKDIR /extract

COPY --from=builder /build/build/libs/*.jar app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --launcher --destination out
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
ARG EXPOSED_PORT
EXPOSE ${EXPOSED_PORT}
COPY --from=extract /extract/out/dependencies ./
COPY --from=extract /extract/out/spring-boot-loader ./
COPY --from=extract /extract/out/snapshot-dependencies ./
COPY --from=extract /extract/out/application ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]