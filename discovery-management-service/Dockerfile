FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} discovery-management-service.jar

ENTRYPOINT [ "java", "-jar", "/discovery-management-service.jar" ]

EXPOSE 8761