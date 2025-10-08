package com.algaworks.algashop.product.catalog.presentation;

import com.algaworks.algashop.product.catalog.applicatioin.product.management.ProductInput;
import com.algaworks.algashop.product.catalog.applicatioin.product.management.ProductManagementApplicationService;
import com.algaworks.algashop.product.catalog.applicatioin.product.query.PageModel;
import com.algaworks.algashop.product.catalog.applicatioin.product.query.ProductDetailOutput;
import com.algaworks.algashop.product.catalog.applicatioin.product.query.ProductQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductQueryService productQueryService;

    private final ProductManagementApplicationService productManagementApplicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDetailOutput create(@RequestBody @Valid ProductInput input){
        UUID productId = productManagementApplicationService.create(input);
        return productQueryService.findById(productId);
    }

    @GetMapping("/{productId}")
    public ProductDetailOutput findById(@PathVariable UUID productId) {
        return productQueryService.findById(productId);
    }

    @GetMapping
    public PageModel<ProductDetailOutput> filter(@RequestParam(name = "size", required = false) Integer size,
                                                 @RequestParam(name = "number", required = false) Integer number){
        return productQueryService.filter(size, number);
    }


}
