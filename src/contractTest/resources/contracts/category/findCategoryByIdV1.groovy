package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        headers {
            accept "application/json"
        }
        url("/api/v1/categories/9f41b2e7-6c38-48d1-a9f5-2e7d3a1c4b69")
    }
    response {
        status 200
        headers {
            contentType "application/json"
        }
        body([
                id: fromRequest().path(3),
                name: "Notebook",
                enabled: true
        ])
    }
}