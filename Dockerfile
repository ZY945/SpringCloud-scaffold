FROM openjdk:11-jre-slim-buster
# openjdk:11 654.2MB openjdk:11-jre-slim-buster

ADD target/*.jar /app.jar

WORKDIR /jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","/app.jar"]

