# training-async-architecture

This is the demo project that was built during the training ["Асинхронная архитектура"](https://education.borshev.com/architecture)

---
## Disclaimer
:warning: Some things were done on purpose and simple in the project because of the nature of the course.

---
## Architecture
The project is split into 3 main microservices:
- Auth. This service can register users and generate JWT token for authentication and authorization. 
Each user has a role. According to the requirements only admin can add users. Admin is 
added to DB during the start of the service 
- Task Tracker. The main goal of this service is to manipulate tasks and calculate price per each task
- Accounting. This service is dealing with payment, billing cycles, etc. In short, everything that
is related to finance of the company
- [Schema registry](https://docs.confluent.io/platform/current/schema-registry/index.html). The model of 
domain and streaming events are defined with schema. So schema registry validates the model, check 
backward/forward compatibility, type safety, etc. The message schemas are written with [Avro](https://docs.confluent.io/platform/current/schema-registry/fundamentals/serdes-develop/serdes-avro.html) 

The one service that is missing is analytics.

---
## Setup
In the beginning it is required to start kafka and kafka schema registry:
- Go to *kafka-docker-compose* module
- Run `docker compose up` to start kafka, schema registry, zookeper and kafka ui
Kafka Schema Registry CLI is accessible [here](http://localhost:8081/subjects) and 
Kafka UI is [here](http://localhost:8082/subjects)

Each service contains *Docker file* that build service and
*docker compose* yml file that starts container with PostgreSQL and a service.
In order to deploy any service execute in the module `docker compose up`.

---
## Stack
- Kotlin 1.8
- Spring Boot 3.1.x
- Spring Security
- Kafka
- Avro 1.11.x
- PostgreSQL 15.x

## Homework
- [Homework#0](https://miro.com/app/board/uXjVMyAaRDs=/?share_link_id=723311522393)
- Homework#1 – Event Storming and Domain Model [diagrams](https://miro.com/app/board/uXjVMxPSYUw=/?share_link_id=202795793480)
- Homework#3 – This homework included answers in the [Homework-03.md](docs%2FHomework-03.md)

Other homeworks are implemented with the code.

---
## Certificate
The certificate that I passed "Async Architecture" course from *July 28
to August 28, 2023* is below!

![certificate.png](docs%2Fcertificate.png)