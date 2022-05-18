FROM adoptopenjdk/openjdk11:latest
MAINTAINER iquinto.com
VOLUME /tmp
EXPOSE 8080
ADD build/libs/working-student-0.0.1-SNAPSHOT.jar workingstudent.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/workingstudent.jar"]