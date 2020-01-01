FROM java:8-jdk-alpine
COPY ./target/doodle-polls.war /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/app/doodle-polls.war"]