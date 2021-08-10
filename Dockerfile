FROM openjdk:8-alpine
ARG JAR_FILE
COPY launcher/target/${JAR_FILE} app.jar
RUN mkdir -p /site/wwwroot/temp/
RUN apk --no-cache add curl
ENTRYPOINT ["java","-jar","/app.jar"]
