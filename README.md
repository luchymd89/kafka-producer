docker-compose up
run de App.java

docker exec -it kafka-broker bash
kafka-topics --bootstrap-server kafka-broker:9092 --create --topic primer-topic
kafka-console-consumer --bootstrap-server kafka-broker:9092 --topic primer-topic --from-beginning