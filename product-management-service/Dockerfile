FROM openjdk:11

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} product-management-service.jar

ENTRYPOINT [ "java", "-jar", "/product-management-service.jar" ]

EXPOSE 8080