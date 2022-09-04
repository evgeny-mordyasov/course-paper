FROM openjdk:11

ADD /target/course-paper-web-1.0-SNAPSHOT.jar backend.jar

RUN mkdir /usr/local/storage

ENTRYPOINT ["java", "-jar", "backend.jar"]