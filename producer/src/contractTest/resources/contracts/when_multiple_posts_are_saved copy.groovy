package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    description 'Should save a post and return the saved post with a Content-Type header'

    request {
        urlPath '/posts/all'
        method HttpMethods.POST
        headers {
            header(accept(), applicationJson())
            header(contentType(), applicationJson())
        }
        body(
                [
                        [
                                id     : $(consumer(null), producer(1)), // Allow null for id during creation
                                title  : $(consumer(anyNonEmptyString()), producer('Tool')), // Title string
                                content: $(consumer(anyNonEmptyString()), producer('Gradle')), // Content string
                                userId : $(consumer(anyInteger()), producer(1)) // ISO LocalDateTime format
                        ],
                        [
                                id     : $(consumer(null), producer(2)), // Allow null for id during creation
                                title  : $(consumer(anyNonEmptyString()), producer('Test')), // Title string
                                content: $(consumer(anyNonEmptyString()), producer('Spring Contract')), // Content string
                                userId : $(consumer(anyInteger()), producer(1)) // ISO LocalDateTime format
                        ]
                ]
        )
    }

    response {
        status CREATED()
        headers {
            header(contentType(), applicationJson())
        }
        body(
                [
                        [
                                id     : 1,
                                title  : 'Tool',
                                content: 'Gradle',
                                userId : 1
                        ],
                        [
                                id     : 2,
                                title  : 'Test',
                                content: 'Spring Contract',
                                userId : 1
                        ]
                ]
        )
    }
}
