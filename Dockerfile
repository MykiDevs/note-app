FROM eclipse-temurin:25-jdk-alpine AS builder
WORKDIR /app
COPY gradlew .
COPY ./build.gradle settings.gradle ./
COPY gradle gradle

RUN ./gradlew dependencies --no-daemon
COPY src src
RUN ./gradlew clean bootJar --no-daemon -x test

FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]