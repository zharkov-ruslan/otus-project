FROM bellsoft/liberica-openjdk-debian:latest
#EXPOSE 8080
ARG JAR_FILE=target/docker-demo-0.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]