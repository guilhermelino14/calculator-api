docker exec -it calculator-api-kafka-1 bash
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic calculator-operations

kafka-topics.sh --bootstrap-server localhost:9092 --list // verificar se existe topicos
