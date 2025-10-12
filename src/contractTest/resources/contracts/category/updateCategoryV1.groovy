package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()
        headers {
            accept 'application/json'
            contentType 'application/json'
        }
        urlPath("/api/v1/categories/4e9b1a73-2c56-4f8d-93a7-b2d5e61f0c48") {
            body([
                    name: value(
                            test("Notebook"),
                            stub(nonBlank())
                    ),
                    enabled: value(
                            test(false),
                            stub(anyBoolean())
                    )
            ])
        }
    }
    response {
        status 200
        headers {
            contentType 'application/json'
        }
        body([
                id: anyUuid(),
                name: fromRequest().body('$.name'),
                enabled: anyBoolean()
        ])
    }
}