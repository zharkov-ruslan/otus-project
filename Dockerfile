FROM bellsoft/liberica-openjdk-debian:latest
#EXPOSE 8000
ARG JAR_FILE=target/home-work-0.4.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]