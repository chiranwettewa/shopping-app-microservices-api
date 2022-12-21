FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} configuration-management-service.jar

ENTRYPOINT [ "java", "-jar", "/configuration-management-service.jar" ]

EXPOSE 9296