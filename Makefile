.PHONY: help cleanp buildp publishp testp cbp cleanc buildc debugc testp

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

publishp:
	./gradlew :producer:publish

testp:
	./gradlew :producertest

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

cbp: cleanp buildp publishp
	echo 'clean build and publish'

