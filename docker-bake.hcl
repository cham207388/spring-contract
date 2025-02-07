# include {
#     path = "./variables.hcl"
# }

variable "IMAGE_TAG" {
    default = "latest"
}

variable "DOCKER_USERNAME" {
    default = "baicham"
}

variable "PRODUCER" {
    default = "spring-contract-consumer"
}

variable "CONSUMER" {
    default = "spring-contract-producer"
}

group "default" {
    targets = ["consumer", "producer"]
}

target "consumer" {
    context = "./consumer"
    dockerfile = "Dockerfile"
    tags = ["${DOCKER_USERNAME}/${CONSUMER}:${IMAGE_TAG}"]
    # platforms = ["linux/amd64", "linux/arm64"]
}

target "producer" {
    context = "./producer"
    dockerfile = "Dockerfile"
    tags = ["${DOCKER_USERNAME}/${PRODUCER}:${IMAGE_TAG}"]
    # platforms = ["linux/amd64", "linux/arm64"]
}