FROM openjdk:17
LABEL authors="zexinxu"

RUN mkdir -p /usr/qq_robot/src/message_service_2

WORKDIR /usr/qq_robot/src/message_service_2

COPY ./message_service_2-1.0-SNAPSHOT.jar /usr/qq_robot/src/message_service_2

ENTRYPOINT ["java", "-jar", "message_service_2-1.0-SNAPSHOT.jar"]