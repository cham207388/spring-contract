package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    description "Should return post for a given userId and postId"

    request {
        method HttpMethods.GET
        urlPath('/posts/1/users/1')
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                id: 1,
                title: "Tool",
                content: "Gradle",
                userId: 1
        )
    }
}
