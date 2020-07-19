FROM openjdk:8-jdk-alpine
COPY target/user-crud-0.0.1.jar /usr/src/myapp/app.jar
WORKDIR /usr/src/myapp
EXPOSE 8080
CMD ["java", "-Xms512M", "-Xmx768M", "-jar", "app.jar"]