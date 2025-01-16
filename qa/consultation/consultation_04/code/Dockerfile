FROM docker.io/gradle:7.5.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon --stacktrace

FROM docker.io/alpine:3.16.3

RUN apk update && apk --no-cache add openjdk11
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk/
EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/app.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
