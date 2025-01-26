package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    description 'Should save a post and return the saved post with a Content-Type header'

    request {
        urlPath '/api/v1/posts'
        method HttpMethods.POST
        headers {
            header(accept(), applicationJson())
            header(contentType(), applicationJson())
        }
        body([
                id     : $(consumer(null), producer(1)),
                title  : $(consumer(anyNonEmptyString()), producer('Tool')),
                content: $(consumer(anyNonEmptyString()), producer('Gradle')),
                userId   : $(consumer(anyInteger()), producer(1)),
                createdAt : $(consumer(null), producer(anyDateTime()))
        ])
    }

    response {
        status CREATED()
        headers {
            header(contentType(), applicationJson())
        }
        body(
                id: 1,
                title: 'Tool',
                content: 'Gradle',
                userId: 1,
                createdAt: anyDateTime()
        )
    }
}
