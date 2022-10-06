FROM openjdk:8-jre-alpine
ENV SERVER_HOST=localhost
COPY target/broker-*-jar-with-dependencies.jar /broker.jar
CMD /usr/bin/java -jar /broker.jar $SERVER_HOST