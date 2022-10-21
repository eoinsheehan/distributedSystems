run mvn clean install

with docker running
run docker-compose build
- this will build the broker and the web services
run docker-compose up --force-recreate

the client can also be run in maven using
mvn compile exec:java -pl client 

this should return quotes from each of the dockerised services
