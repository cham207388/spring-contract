# Contract Testing

Testing the API contract between the producer and consumer is a good way to ensure that the API is working as expected. With the help of Spring Cloud Contract, we can easily test the API contract between the producer and consumer without the need to create our own stubs or spin up the producer's service.

## Resources

- [Udemy](https://www.udemy.com/course/contract-testing-with-spring-cloud-contract/)
- [Spring Cloud Contract Reference](https://docs.spring.io/spring-cloud-contract/reference/index.html)
- [Contract test guide](https://spring.io/guides/gs/contract-rest)
- [Software Mill blog](https://softwaremill.com/contract-testing-spring-cloud-contract/)
- [DSL Dynamic Properties](https://docs.spring.io/spring-cloud-contract/reference/project-features-contract/dsl-dynamic-properties.html)

## Producer side

This is the service being queried by the consumer either by using WebClient or RestTemplate or RestClient to name a few rest clients. It's also called the server.

### Producer setup

- add `org.springframework.cloud:spring-cloud-starter-contract-verifier` dependency
- write your contracts (**groovy**, kotlin, java, or yaml) in src/contractTest/resources/contracts folder
  - /http/ for http client contracts
    - [example http contract](producer/src/contractTest/resources/contracts/http/when_one_post_is_saved.groovy) 
  - /jms/ for jms message contracts
    - [example jms contract](producer/src/contractTest/resources/contracts/jms/when_one_post_is_published_to_amq.groovy)
  - `./gradlew build` is responsible for generating the stubs and running the tests: will likely fail here
  - a `build/generated-test-sources/contractTest/ContractVerifierTest` class will be created from the contracts.
- create a `Base Test` file
  - this configures will be used by the test class generated from the contracts
- update the contract closure in the build.gradle file to use the Base Test classes. One for https contracts and another for jms contracts

  ```groovy
        contracts {
           testFramework = 'JUnit5'
           packageWithBaseClasses = 'com.abc.contracts.producer'

           baseClassMappings {
               baseClassMapping('.*http.*', 'com.abc.contracts.producer.BaseHttpTest') // HTTP tests
               baseClassMapping('.*jms.*', 'com.abc.contracts.producer.BaseJmsTest')   // JMS tests
           }
        }
  ```

- publishing the stubs to be used by the consumer
  - add publishing method and mvn plugin to the `producer` build.gradle file to publish the stubs to the local maven repository

  ```groovy

    plugins {
      id 'maven-publish'
      id 'org.springframework.cloud.contract' version '4.2.0'
    }

    publishing {
      publications {
        stubs(MavenPublication) {
            artifactId = "${project.name}"
            artifact ("${buildDir}/libs/${project.name}-${version}-stubs.jar"){
                classifier = 'stubs'
            }
        }
      }
    repositories {
        mavenLocal() // Publishes to the local Maven repository (~/.m2/repository)
    }
  }
  ```

- `./gradlew build`
  - builds the producer along with running the tests, and generate the stubs
- `./gradlw publish`
  - publishes the stubs to the local maven repository
  - make sure that the version is not SNAPSHOT (timestamp in the name instead of SNAPSHOT)
  - I'm using RELEASE version for the producer
  - note the groupId, artifactId, version, and qualifier as they'll required in the consumer side's integration tests class

## Consumer side

This is the service that is calling the producer. It's also called the client.

### Consumer setup

- add `org.springframework.cloud:spring-cloud-starter-contract-stub-runner` dependency
- write your integration tests
  - [example integration test](consumer/src/test/java/com/abc/contracts/consumer/services/PostIntegrationTest.java)
- run the tests
- these tests will hit the stubs created by the producer instead of interacting with the producer's service directly. Pretty cool!

## Swagger

- [producer swagger](http://localhost:8081/swagger-ui/index.html#/)
- [consumer swagger](http://localhost:8085/swagger-ui/index.html#/)

## Learning Ideas

- Spring Rest Clients
  - RestClient
  - WebClient
- how to use prioritise in WireMock
  - [priority explain](https://stackoverflow.com/questions/50078978/how-to-use-priorities-in-wiremock)

## Dockerized the application

### Services

- consumer
- producer
- postgres
- zipkin
- activemq

## Makefile

- with dynamic values:
  - ```make dc-producer version=v1```
