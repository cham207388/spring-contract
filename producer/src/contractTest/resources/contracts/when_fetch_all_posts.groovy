/* groovylint-disable DuplicateStringLiteral */
package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    request {
        urlPath '/posts'
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
                        userId : 1
                ],
                [
                        id     : 2,
                        title  : 'Test',
                        content: 'Spring Cloud Contract',
                        userId : 1
                ],
                [
                        id     : 3,
                        title  : 'Test',
                        content: 'Contract Testing',
                        userId : 2
                ]
        ])
    }
    priority(2) // the first contract to be added has the least priority: higher number
}