variable "IMAGE_TAG" {
    default = "latest"
}

variable "DOCKER_USERNAME" {
    default = "baicham"
}

variable "PROJECT_BASE" {
    default = "spring-contract"
}

group "default" {
    targets = ["consumer", "producer"]
}

target "consumer" {
    context = "./consumer"
    dockerfile = "Dockerfile"
    tags = ["${DOCKER_USERNAME}/${PROJECT_BASE}-consumer:${IMAGE_TAG}"]
    # platforms = ["linux/amd64", "linux/arm64"]
}

target "producer" {
    context = "./producer"
    dockerfile = "Dockerfile"
    tags = ["${DOCKER_USERNAME}/${PROJECT_BASE}-producer:${IMAGE_TAG}"]
    # platforms = ["linux/amd64", "linux/arm64"]
}