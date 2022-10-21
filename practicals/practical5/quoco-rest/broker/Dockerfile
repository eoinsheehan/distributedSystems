FROM openjdk:8-jre-alpine
COPY target/broker-0.0.1.jar /broker.jar
CMD /usr/bin/java -jar /broker.jar  --server.urls=$URLS