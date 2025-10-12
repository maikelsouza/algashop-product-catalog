package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        headers {
            accept "application/json"
        }
        url("api/v1/categories/d6a3f9b2-18e4-4c75-9b1d-7e2a64c3f8d9")
    }
    response {
        status 404
        body([
                instance: fromRequest().path(),
                type: "/errors/not-found",
                title: "Not Found"
        ])
    }



}