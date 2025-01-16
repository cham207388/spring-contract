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
        body(
                id: null,
                title: $(consumer(regex('[a-zA-Z]+')), producer('Tool')),
                content: $(consumer(regex('[a-zA-Z]+')), producer('Gradle')),
                userId: $(consumer(anyNumber()), producer(1))
        )
    }

    response {
        status OK()
        headers {
            header 'Content-Type': 'application/json'
        }
        body(
                id: 1, // Server generates ID
                title: 'Tool', // Server echoes back the title
                content: 'Gradle', // Server echoes back the content
                userId: 1 // Server echoes back the userId
        )
    }
}
