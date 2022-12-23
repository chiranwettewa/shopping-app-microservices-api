FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} payment-management-service.jar

ENTRYPOINT [ "java", "-jar", "/payment-management-service.jar" ]

EXPOSE 8081