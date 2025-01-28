package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should publish a Post message to RabbitMQ"

    // Input message details
    input {
        triggeredBy("postService.savePosts([new Post(title: 'Sample Title', content: 'Sample Content', userId: 42)])")
    }

    // Output message details
    outputMessage {
        sentTo "post-queue" // Name of the queue
        body([
                id       : null, // ID can be null when the message is published
                title    : "Sample Title",
                content  : "Sample Content",
                userId   : 42,
                createdAt: null // createdAt can be null when publishing
        ])
        headers {
            header("contentType", applicationJson())
        }
        bodyMatchers {
            jsonPath('$.title', byRegex(nonEmpty())) // Validate title is a non-empty string
            jsonPath('$.content', byRegex(nonEmpty())) // Validate content is a non-empty string
            jsonPath('$.userId', byType()) // Validate userId exists
            jsonPath('$.createdAt', byRegex(".*")) // Allow createdAt to be null or any string
        }
    }
}