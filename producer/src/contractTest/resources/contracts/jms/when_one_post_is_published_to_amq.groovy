package contracts.jms

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name("post_message_contract")
    description("Validates that a Post message is correctly published to the queue")
    priority 1

    input {
        triggeredBy("triggerPostMessage()") // Explicitly call from BaseJmsTest
    }

    outputMessage {
        sentTo("post-queue")

        body(
                id: $(regex('-?\\d+')),
                title: $(regex('.+')),
                content: $(regex('.+')),
                userId: $(regex('\\d+')),
                createdAt: $(regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}"))
        )

        headers {
            header("_type", "com.abc.contracts.producer.domains.Post")
            header("JMSType", "application/json")
            header("Content_Type", "application/json")
        }
    }
}
