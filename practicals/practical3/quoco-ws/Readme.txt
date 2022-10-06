run mvn clean install
run docker-compose build
- this will build the broker and the web services
run docker-compose up --force-recreate

in a separate terminal can create the client image
docker build -t client:latest .
docker run --network=host client:latest - to run the client on the host network

the client can also be run in maven using
mvn compile exec:java -pl client

this should return quotes from each of the dockerised services

I interpreted the practical sheet to not require the client to be within the docker compose file.