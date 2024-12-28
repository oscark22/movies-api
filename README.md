### Run the postgres db container
docker run -p 127.0.0.1:5000:5432 --name postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres

### Run the kafka container
docker run -p 127.0.0.1:9092:9092 -d --name broker apache/kafka:latest
