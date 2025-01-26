package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    description 'Should return post for a given userId and postId'

    request {
        method HttpMethods.GET
        url value(regex('/api/v1/posts/[1-9][0-9]?/users/[1-9][0-9]?'))
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
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
