FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} order-management-service.jar

ENTRYPOINT [ "java", "-jar", "/order-management-service.jar" ]

EXPOSE 8082