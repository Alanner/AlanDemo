FROM frolvlad/alpine-oraclejdk8:slim
WORKDIR /tmp
ADD workmaster-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8000
CMD [ "java", "-jar", "/tmp/app.jar" ]
