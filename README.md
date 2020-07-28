#### Swagger

1. API exposed : http://localhost:8080/swagger-ui.html
2. APIs exposed : http://localhost:8090/swagger-ui.html

#### Two services
1. userService
    - running on port 8080
2. walletService
    - running on port 8090

#### DB user
`create database wallet;`
`create database user;`

#### Redis
1. start a standalone redis server
$ `redis-server`
2. delete a key
`redis-cli del user`


#SMS
https://www.twilio.com/console

#### Kafka

1. start ZooKeeper server
`bin/zookeeper-server-start.sh config/zookeeper.properties`

2. change #listeners=PLAINTEXT://:9092 in server.properties to:

`listeners=PLAINTEXT://localhost:9092`

3. start the Kafka server
`bin/kafka-server-start.sh config/server.properties`



