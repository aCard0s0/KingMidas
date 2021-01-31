# 1st Stage - Build
FROM maven:3.6-openjdk-11 AS bluider
COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean install
CMD ["mvn", "clean", "install"]
WORKDIR /app/target
CMD ["java", "-jar", "xBrokerConnector.jar"]

# 2nd Stage - Deploy & Run
#FROM openjdk:11-jre AS deploy
#COPY --from=builder app/target/xBrokerConnector.jar /app
#WORKDIR /app
#CMD ["java", "-jar", "xBrokerConnector.jar"]

# Ref: https://codefresh.io/docker-tutorial/java_docker_pipeline/
