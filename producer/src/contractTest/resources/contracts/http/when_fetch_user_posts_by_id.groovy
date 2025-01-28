package contracts.http

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    request {
        description 'Should return all posts by user with id = 1'
        urlPath('/api/v1/posts/users') {
            queryParameters {
                parameter('userId', regex('[1-9][0-9]?'))
            }
        }
        method HttpMethods.GET
        headers {
            header(accept(), applicationJson())
        }
    }
    response {
        status 200
        headers {
            header(contentType(), applicationJson())
        }
        body([
                [
                        id     : 1, // List elements must be maps with proper syntax
                        title  : 'Tool',
                        content: 'Gradle',
                        userId : 1,
                        createdAt: anyDateTime()
                ],
                [
                        id     : 2,
                        title  : 'Test',
                        content: 'Spring Cloud Contract',
                        userId : 1,
                        createdAt: anyDateTime()
                ]

        ])
    }
    priority(1) // the first contract to be added has the least priority: higher number
}
