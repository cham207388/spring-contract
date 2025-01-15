package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {

    request {
        urlPath '/posts/'
        method HttpMethods.POST
        headers {
            header(accept(), applicationJson())
        }
        body(
          title: $(consumer(regex('[a-zA-Z]+'))),
          content: $(consumer(regex('[a-zA-Z]+'))),
          userId: anyInteger()
        )
    }
    response {
        status 200
        headers {
            header(contentType(), applicationJson())
        }
        body([
                id: 1,
                title: 'Tool',
                content: 'Gradle',
                userId: 1
        ])
    }
    priority(2) // the first contract to be added has the least priority: higher number
}
