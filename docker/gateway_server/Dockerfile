FROM openjdk:17
LABEL authors="zexinxu"

RUN mkdir -p /usr/qq_robot/src/gateway_server

WORKDIR /usr/qq_robot/src/gateway_server

COPY ./gateway_server-1.0-SNAPSHOT.jar /usr/qq_robot/src/gateway_server

ENTRYPOINT ["java", "-jar", "gateway_server-1.0-SNAPSHOT.jar"]