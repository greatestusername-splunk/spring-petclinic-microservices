FROM eclipse-temurin:17

WORKDIR /application

# Build arguments
ARG ARTIFACT_NAME

# Copy the application JAR directly
COPY target/${ARTIFACT_NAME}.jar application.jar

COPY splunk-otel-javaagent.jar /application/splunk-otel-javaagent-2.16.0.jar
COPY splunk-otel-javaagent.jar /agent/splunk-otel-javaagent-2.16.0.jar

# Install dockerize
RUN wget -O dockerize.tar.gz https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-alpine-linux-amd64-v0.6.1.tar.gz \
    && tar -xzf dockerize.tar.gz \
    && chmod +x dockerize \
    && cp dockerize /usr/local/bin/

# Expose the application port
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=docker

RUN ls -l /application
RUN jar tf /application/application.jar | grep JarLauncher

# Entry point to run the application
#ENTRYPOINT ["./dockerize","-wait=tcp://discovery-server:8761","-timeout=60s","--","java", "-javaagent:/application/splunk-otel-javaagent-2.16.0.jar", "org.springframework.boot.loader.JarLauncher"]
ENTRYPOINT ["java", "-jar", "application.jar"]

