**Simple Spring Cloud Application - Microservice architecture**

Simple microservice architecture using spring cloud framework to orchestrate
the gradlew modules providing features below: 

<img width="880" alt="Functional services" src="https://s3.amazonaws.com/scrummps.com/readme-diagram.png">


### API Gateway ([bot-gateway](https://github.com/thiaguimcavalcanti/simple-spring-cloud-project/tree/master/bot-gateway))

Method	| Path	| Description	| User authenticated	|
------------- | ------------------------- | ------------- |:-------------:
POST	| http://localhost:8080/api/signin	| Logging into the application and returns a JWT token  |
POST	| http://localhost:8080/api/signout	| User logout	| × |
GET	| http://localhost:8080/api/ticks/ticker/{MARKET} | Returns a ticker from the requested market	| x |
GET	| http://localhost:8080/api/ticks/balances | List account balances across available currencies.	| x |
GET	| http://localhost:8080/api/orders/greeting	| Just for tests	| x |

### How to run it?

Execute the follow instruction for each module that you want to run:

    `/.gradlew bootRun`

Suggested order to start the microservices:
1. bot-config
2. bot-eureka
3. bot-gateway
4. bot-orders
5. bot-ticks