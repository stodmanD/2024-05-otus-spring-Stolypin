#FROM maven:3.9.9-amazoncorretto-8-al2023 AS builder
#WORKDIR /build
#COPY . .
#RUN mvn --no-transfer-progress -Dmaven.test.skip=true clean package

#FROM azul/zulu-openjdk:17-latest
FROM bellsoft/liberica-openjdk-alpine-musl:21.0.1
#WORKDIR /app
#COPY --from=builder /build/target/hw16-Docker-0.0.1-SNAPSHOT.jar /app/app.jar

COPY /target/hw16-Docker-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]