FROM ubuntu:latest
LABEL authors="zexinxu"

RUN mkdir -p /usr/qq_robot/src/jx3_service

WORKDIR /usr/qq_robot/src/jx3_service

COPY ./jx3_service-1.0-SNAPSHOT.jar /usr/qq_robot/src/jx3_service

ENTRYPOINT ["java", "-jar", "jx3_service-1.0-SNAPSHOT.jar"]