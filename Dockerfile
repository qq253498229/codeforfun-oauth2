FROM maven:3-jdk-8-alpine as builder

COPY pom.xml /
COPY src /src

RUN mvn clean package && mkdir /app && cp -R ./target /app

FROM java:8-jre

COPY --from=builder /app/target/oauth.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]