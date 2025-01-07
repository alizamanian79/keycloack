# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8081

# Set environment variables
ENV SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=http://localhost:8080/realms/demo
ENV SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI=${SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI}/protocol/openid-connect/certs
ENV SERVER_PORT=8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
