package contracts.jms

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name("post_message_contract")
    description("Validates that a Post message is correctly published to the queue")
    priority 1

    input {
        triggeredBy("triggerPostMessage()") // Calls test method to publish message
    }

    outputMessage {
        sentTo("post-queue") // Ensure the message is sent to the correct queue

        body([
                id       : $(regex('-?\\d+')), // Ensures it's a number (negative or positive)
                title    : $(regex('.+')), // Ensures it's a non-empty string
                content  : $(regex('.+')), // Ensures it's a non-empty string
                userId   : $(regex('\\d+')), // Ensures it's a numeric userId
                createdAt: $(regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")) // ISO 8601 datetime format
        ])

        headers {
            contentType(applicationJson())
        }
    }
}
