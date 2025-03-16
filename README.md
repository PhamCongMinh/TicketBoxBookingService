# TicketBox Booking Service

## General information <hr>

This project is a ticket booking service that allows users to book tickets for various events. 

## Requirement <hr>
- requires Java version 17
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