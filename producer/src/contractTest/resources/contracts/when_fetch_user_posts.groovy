package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    request {
        urlPath( '/posts') {
            queryParameters {
                parameter('userId', equalTo(1))
            }
        }
        method HttpMethods.GET
        headers {
            header(accept(), applicationJson())
        }
        body(userId         : anyInteger())
    }
    response {
        status 200
        headers {
            header(contentType(), applicationJson())
        }
        body([
                userId: anyInteger(), // Correct JSON-like syntax
                posts: [
                        [
                                id: 1, // List elements must be maps with proper syntax
                                title: "Tool",
                                content: "Gradle",
                                userId: 1
                        ],
                        [
                                id: 2,
                                title: "Test",
                                content: "Spring Cloud Contract",
                                userId: 1
                        ]
                ]
        ])
    }
    priority(1) // the first contract to be added has the least priority (higher number)
}