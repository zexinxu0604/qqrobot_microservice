FROM openjdk:17
LABEL authors="zexinxu"

RUN mkdir -p /usr/qq_robot/src/group_image_service_1

WORKDIR /usr/qq_robot/src/group_image_service_1

COPY ./group_image_service_1-1.0-SNAPSHOT.jar /usr/qq_robot/src/group_image_service_1

ENTRYPOINT ["java", "-jar", "group_image_service_1-1.0-SNAPSHOT.jar"]