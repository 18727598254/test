FROM java:8
VOLUME /tmp
ADD hx-system-2.0.jar app.jar
ARG JAR_FILE=fix.jar
RUN bash -c "touch /app.jar"
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]