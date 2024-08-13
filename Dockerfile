FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/core-api-0.1-all.jar /app/core-api.jar

ARG JAVA_PORT
ENV JAVA_PORT=${JAVA_PORT:-8080}

CMD ["bash", "-c", "java -Dmicronaut.server.port=${JAVA_PORT} -jar /app/core-api.jar "]

EXPOSE ${JAVA_PORT}