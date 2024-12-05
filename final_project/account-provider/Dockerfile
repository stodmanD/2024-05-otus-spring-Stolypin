FROM docker.io/bellsoft/liberica-openjdk-alpine:17
COPY ./target/account-provider.jar /opt/account-provider.jar
ENTRYPOINT ["java", "-jar", "/opt/account-provider.jar"]
