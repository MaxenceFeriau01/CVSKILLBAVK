FROM openjdk:15-jdk-alpine

RUN mkdir /app
RUN mkdir /app/logs
WORKDIR /app

RUN apk update && apk add --no-cache openssl && rm -rf "/var/cache/apk/*"

COPY target/*.jar /app/app.jar
COPY dockerstart.sh /app/dockerstart.sh
RUN chmod +x dockerstart.sh

EXPOSE 8080

ENTRYPOINT ["/bin/sh", "/app/dockerstart.sh"]
