package contracts.messaging

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should publish a Post message to RabbitMQ"

    input {
        // Use the correct fully-qualified Post class
        triggeredBy("getPostService().save(new com.abc.contracts.producer.domains.Post(\"Sample Title\", \"Sample Content\", 42))")
    }

    outputMessage {
        sentTo "post-queue"
        body(
                [id       : null,
                 title    : "Sample Title",
                 content  : "Sample Content",
                 userId   : 42,
                 createdAt: null]
        )
        headers {
            header("contentType", applicationJson())
        }
        bodyMatchers {
            jsonPath('$.title', byRegex(nonEmpty()))
            jsonPath('$.content', byRegex(nonEmpty()))
            jsonPath('$.userId', byType())
            jsonPath('$.createdAt', byRegex(".*"))
        }
    }
}