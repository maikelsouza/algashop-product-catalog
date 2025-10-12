package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    request {
        method DELETE()
        headers {
            accept 'application/json'
        }
        url("/api/v1/categories/f9c42e81-7a63-4d1b-b8e5-2a7f39c6d4b1")
    }

    response {
        status 404
    }
}