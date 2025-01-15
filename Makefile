.PHONY: help cleanp buildp publish testp cbp cbc cleanc buildc debugc testp database

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
	docker container run --rm --name contract -e POSTGRES_DB=contract -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres

	