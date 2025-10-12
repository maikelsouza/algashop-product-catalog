package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()
        headers {
            accept "application/json"
            contentType 'application/json'
        }
        urlPath("/api/v1/categories/b218f4c7-9d53-46a2-8e71-3fa9c2b6d4e8") {
            body([
                    name   : value(
                            test("Notebook"),
                            stub(nonBlank())
                    ),
                    enabled : value(
                            test(true),
                            stub(anyBoolean())
                    )
            ])
        }
    }
    response {
        status 404
        body([
                instance: fromRequest().path(),
                type    : "/errors/not-found",
                title   : "Not Found"
        ])
    }
}