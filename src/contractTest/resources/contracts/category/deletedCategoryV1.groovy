package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    request {
        method DELETE()
        headers {
            accept 'application/json'
        }
        url("/api/v1/categories/a37d5c29-b184-48e3-9f62-7c1b0de54a9f")
    }

    response {
        status 204
    }
}