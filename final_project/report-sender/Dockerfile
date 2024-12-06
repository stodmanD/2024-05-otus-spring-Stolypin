FROM docker.io/bellsoft/liberica-openjdk-alpine:17
COPY ./target/report-sender.jar /opt/report-sender.jar
ENTRYPOINT ["java", "-jar", "/opt/report-sender.jar"]
