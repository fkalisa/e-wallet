#### Two services
1. userService
2. walletService

##### userService
1. running on port 8080
2. API exposed : http://localhost:8080/swagger-ui.html

##### walletService
1. running on port 8090
2. APIs exposed : http://localhost:8090/swagger-ui.html


#### DB user
create database wallet;
create database user;

#### Redis
1. start a standalone redis server
$ redis-server
2. delete a key
redis-cli del user