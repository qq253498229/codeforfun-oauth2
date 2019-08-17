FROM maven:3-jdk-8-alpine
WORKDIR /app
COPY pom.xml ./pom.xml
RUN mvn package -P docker -Dmaven.test.skip=true

FROM maven:3-jdk-8-alpine
WORKDIR /app
COPY src ./src
COPY --from=0 /app/pom.xml ./pom.xml
COPY --from=0 /root/.m2 /root/.m2
RUN mvn package -P docker -Dmaven.test.skip=true

FROM openjdk:8-jre-alpine
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
WORKDIR /app
COPY --from=1 /app/target/app.jar ./app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/app/app.jar"]
