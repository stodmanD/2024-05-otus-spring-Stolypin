
FROM bellsoft/liberica-openjdk-alpine-musl:21.0.1

COPY /target/hw16-Docker-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

FROM postgres:13
EXPOSE 15432