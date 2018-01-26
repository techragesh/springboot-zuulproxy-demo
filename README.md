# Springboot ZuulProxy Demo #
This project shows how to configure proxy url in spring microservice. This project has two microservices and configured those service in Eureka Server.
By using Zuul, we have created the proxy url for the two microservices.

# Diagram #

![springcloudzuul.png](springcloudzuul)

# DbService #

* @EnableEurekaClient annotation used to access from EurekaServer

* This service project is a simple rest application it just show the stock information from the database (using mysql).

* Actual service url to access this service is: **http://localhost:8502/rest/db**

* The DbService url has configured in the Eureka Server

```
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    serviceUrl:
      defaultZone: http://localhost:8501/eureka/
  instance:
    hostname: localhost
```

# StockService #

* @EnableEurekaClient annotation used to access from EurekaServer

* This service project is also a simple rest application it just show the stock information from the database.

* But this stockservice called the dbservice for fetching the stock information.

* In simple, _stockservice -> dbservice -> mysql_

* Actual service url to access this service is: **http://localhost:8503/rest/stock**

* The StockService url has configured in the Eureka Server

```
spring:
  application:
    name: stockservice
server:
  port: 8503
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8501/eureka/
  instance:
    hostname: localhost

```

# EurekaService #

* @EnableEurekaServer annotation used to access this service registry.

* @EnableZuulProxy annotation used to access the API Gateway

* This is EurekaServie application. DbService and StockService are configured here.

* On top of that we have configured ZuulProxy to access dbservice and stockservice.

**Note:**

By using zuul proxy, user cannot able to see actual service url. Instead they use proxy url to access the services through eureka server.

```
spring:
  application:
    name: eurekaservice

server:
  port: 8501

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
    server:
      waitTimeInMsWhenSyncEmpty: 0
zuul:
  prefix: /api
  routes:
    dbservice:
      path: /dbservice/**
      url: http://localhost:8502
    stockservice:
      path: /stockservice/**
      url: http://localhost:8503

```
The above configuration explains how proxy url are created. We have created route path name as **dbservice** and **stockservice** with prefix **/api**

* User can able to access only this url
    
    **_To access dbservice:_**
    
    **http://localhost:8501/api/dbservice/rest/db/**
    
    **_To access stockservice:_**
    
    **http://localhost:8501/api/stockservice/rest/stock/**
    
    Here,
    
      **http://localhost:8501** is the Eureka Service URL
      **api** is the Zuul proxy name
      **dbService** is the route path of dbservice url http://localhost:8502
      **stockservice** is the route path of stockservice url http://localhost:8503
      
_### Happy Coding ###_