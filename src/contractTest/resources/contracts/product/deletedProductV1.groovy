package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    request {
        method DELETE()
        urlPath("/api/v1/products/f1d3a7c4-6b2e-4f8a-9217-5d9c2e1b3a4f/enable")
    }

    response {
        status 204
    }
}