# Use an official OpenJDK image as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /opt

# Copy the JAR file into the container
COPY target/*.jar /opt/app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar