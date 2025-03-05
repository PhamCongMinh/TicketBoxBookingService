# TicketBox Booking Service

## General information <hr>

This project includes: APIs and Jobs of the NFT Ticket backend


## Requirement <hr>
- requires Java version 21
- docker

## Set up <hr>


### Run project:
#### Run with docker:
```bash
docker-compose up -d
```
#### Run with file jar:
```bash
mvn clean package -DskipTests
java -jar TicketBox-0.0.1-SNAPSHOT.jar
```

## Project Structure <hr>

## Other command:
Generate Avro classes:
```bash
mvn clean generate-sources
```