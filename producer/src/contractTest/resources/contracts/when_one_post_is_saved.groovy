package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    description 'Should save a post and return the saved post with a Content-Type header'

    request {
        urlPath '/posts'
        method HttpMethods.POST
        headers {
            header(accept(), applicationJson())
            header(contentType(), applicationJson())
        }
        body([
                id     : $(consumer(null), producer(1)), // Allow null for id during creation
                title  : $(consumer(anyNonEmptyString()), producer('Tool')), // Title string
                content: $(consumer(anyNonEmptyString()), producer('Gradle')), // Content string
                userId   : $(consumer(anyInteger()), producer(1)) // ISO LocalDateTime format
        ])
    }

    response {
        status CREATED()
        headers {
            header(contentType(), applicationJson())
        }
        body(
                id: 1, // Server generates ID
                title: 'Tool', // Server echoes back the title
                content: 'Gradle', // Server echoes back the content
                userId: 1 // Server echoes back the userId
        )
    }
}
