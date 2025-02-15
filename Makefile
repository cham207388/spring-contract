.PHONY: help cleanp buildp publish testp cbp cbc cleanc buildc debugc testp database zipkin stop-db producer consumer \
		dc-network producer-image build-images push-images consumer-image dcu dcd deployment dc-bake

version ?= latest

help: ## Show this help message with aligned shortcuts, descriptions, and commands
	@awk 'BEGIN {FS = ":"; printf "\033[1m%-20s %-40s %s\033[0m\n", "Target", "Description", "Command"} \
 /^[a-zA-Z_-]+:/ { \
  target=$$1; \
  desc=""; cmd="(no command)"; \
  if ($$2 ~ /##/) { sub(/^.*## /, "", $$2); desc=$$2; } \
  getline; \
  if ($$0 ~ /^(\t|@)/) { cmd=$$0; sub(/^(\t|@)/, "", cmd); } \
  printf "%-20s %-40s %s\n", target, desc, cmd; \
 }' $(MAKEFILE_LIST)

cleanp:
	./gradlew :producer:clean

buildp:
	./gradlew :producer:build

publish:
	./gradlew :producer:publish

testp:
	./gradlew :producer:test

tasks:
	./gradlew :producer:tasks

cleanc:
	./gradlew :consumer:clean

buildc:
	./gradlew :consumer:build

testc:
	./gradlew :consumer:test

debugc:
	./gradlew :consumer:test --debug-jvm

cbp: cleanp buildp
	echo 'cleaned and built the producer!'

cbc: cleanp buildp
	echo 'cleaned and built the consumer!'
	
database: # 2. start postgres container
	docker container run --rm --name contract-db \
		-e POSTGRES_DB=contract \
		-e POSTGRES_USER=postgres \
		-e POSTGRES_PASSWORD=postgres \
		-p 5432:5432 -d \
		postgres

zipkin:
	docker container run --rm --name zipkin -d -p 9411:9411 openzipkin/zipkin

producer:
	./gradlew :producer:bootRun

consumer:
	./gradlew :consumer:bootRun

# start db, then zipkin, then producer, then consumer, then zipkin
db-zipkin: stop-db database stop-zipkin zipkin
	echo "start database and zipkin"

stop-db:
	docker container stop contract-db

stop-zipkin:
	docker container stop zipkin

## Docker

producer-image:
	docker image build -t baicham/spring-contract-producer:$(version) ./producer

consumer-image:
	docker image build -t baicham/spring-contract-consumer:$(version) ./consumer

push-producer:
	docker image push baicham/spring-contract-producer:$(version)

push-consumer:
	docker image push baicham/spring-contract-consumer:$(version)

build-images: producer-image consumer-image
	echo "build docker images"

push-images: push-producer push-consumer
	echo "docker images pushed to docker hub!"

producer-app:
	docker container run --rm --name contract-db \
#		--name spring-contract-producer \
		--network spring-contract \
		-p 8081:8081 \
        -e DB_URL=jdbc:postgresql://contract-db:5432/contract \
        -e DB_USERNAME=postgres \
        -e DB_PASSWORD=postgres \
        baicham/spring-contract-producer:v1

dc-network:
	docker network create spring-contract

dcu:
	docker compose up --build

dcd:
	docker compose down -v
	
deployment:
	./k8s/manual/deploy.sh

dc-producer: producer-image push-producer
	echo 'build and push producer image to docker hub'

dc-consumer: consumer-image push-consumer
	echo 'build and push consumer image to docker hub'

dc-bake:
	@echo "Using image tag: $(version)"
	@export IMAGE_TAG=$(version) && docker buildx bake --push