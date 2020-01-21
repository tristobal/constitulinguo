# constitulinguo [![Build Status](https://travis-ci.org/tristobal/constitulinguo.svg?branch=master)](https://travis-ci.org/tristobal/constitulinguo)

## Requisitos

Para ejecución local, se necesita tener en segundo plano un contenedor Docker con MongoDB
ej:

```shell
# Descargar la imagen
docker pull mongo

# Ejecutar un contenedor
docker run --rm --name local-mongo -p 27017:27017 mongo
```

## Ejecución

```shell
./mvnw clean package && java -jar -Dspring.profiles.active=dev target/*.jar
```

#### Fuentes
<sup><sup>https://blog.knoldus.com/spring-webflux-testing-your-router-functions-with-webtestclient/
https://developer.okta.com/blog/2018/09/24/reactive-apis-with-spring-webflux</sup></sup>
