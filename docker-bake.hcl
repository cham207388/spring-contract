variable "IMAGE_TAG" {
  default = "latest"
}

variable "DOCKER_USERNAME" {
  default = "baicham"
}

variable "PRODUCER" {
  default = "spring-contract-producer"
}

variable "CONSUMER" {
  default = "spring-contract-consumer"
}

group "default" {
  targets = ["consumer", "producer"]
}

target "consumer" {
  context = "./consumer"
  dockerfile = "Dockerfile"
  tags = ["${DOCKER_USERNAME}/spring-contract-consumer:${IMAGE_TAG}"]
  # platforms = ["linux/amd64", "linux/arm64"]
}

target "producer" {
  context = "./producer"
  dockerfile = "Dockerfile"
  tags = ["${DOCKER_USERNAME}/spring-contract-producer:${IMAGE_TAG}"]
  # platforms = ["linux/amd64", "linux/arm64"]
}