FROM openjdk:17
LABEL authors="zexinxu"

RUN mkdir -p /usr/qq_robot/src/message_service_1

WORKDIR /usr/qq_robot/src/message_service_1

COPY ./message_service_1-1.0-SNAPSHOT.jar /usr/qq_robot/src/message_service_1

ENTRYPOINT ["java", "-jar", "message_service_1-1.0-SNAPSHOT.jar"]