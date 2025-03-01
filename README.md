## Run docker compose
```
docker compose up -d
```

## Verify if the kafka is getting messages

### Enter in the container of kafka
```
docker exec -it calculator-api-kafka-1 bash
```

### List all topics
```
kafka-topics.sh --bootstrap-server localhost:9092 --list
```

### Consume the messages from the topic calculator-operations
```
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic calculator-operations
```

## Send a request to the calculator api
### Sum
```
curl "http://localhost:8080/api/calculator/sum?a=10&b=5"
```

### Subtract
```
curl "http://localhost:8080/api/calculator/subtract?a=10&b=5"
```

### Multiply
```
curl "http://localhost:8080/api/calculator/multiply?a=10&b=5"
```

### Divide
```
curl "http://localhost:8080/api/calculator/divide?a=10&b=5"
```


## Test the calculator api
### Run the tests
```
gradle test
```

### Check the test coverage
```
check rest coverage in the file rest/build/reports/tests/test/index.html
check calculator coverage the file calculator/build/reports/tests/test/index.html
check KafkaCalculatorServiceTest coverage the file calculator/build/reports/tests/test/classes/com.wit.calculator.KafkaCalculatorServiceTest.html
```

## Check The Unique identifiers of the operations
```
curl -i  "http://localhost:8080/api/calculator/divide?a=10&b=5"
```
