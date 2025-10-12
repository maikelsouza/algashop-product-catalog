package com.algaworks.algashop.product.catalog.presentation;

import com.algaworks.algashop.product.catalog.application.category.management.CategoryInput;
import com.algaworks.algashop.product.catalog.application.category.management.CategoryManagementApplicationService;
import com.algaworks.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import com.algaworks.algashop.product.catalog.application.category.query.CategoryQueryService;
import com.algaworks.algashop.product.catalog.application.product.query.PageModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryController {


    private final CategoryManagementApplicationService categoryManagementApplicationService;

    private final CategoryQueryService categoryQueryService;

    @GetMapping
    public PageModel<CategoryDetailOutput> filter(@RequestParam(name = "size", required = false) Integer size,
                                                  @RequestParam(name = "page", required = false) Integer page){
        return categoryQueryService.filter(size, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDetailOutput create(@RequestBody @Valid CategoryInput input){
        UUID categoryId = categoryManagementApplicationService.create(input);
        return categoryQueryService.findById(categoryId);
    }
    @GetMapping("/{categoryId}")
    public CategoryDetailOutput findById(@PathVariable UUID categoryId) {
        return categoryQueryService.findById(categoryId);
    }

    @PutMapping("/{categoryId}")
    public CategoryDetailOutput update(@PathVariable UUID categoryId,
                                      @RequestBody @Valid CategoryInput input) {
        categoryManagementApplicationService.update(categoryId, input);
        return categoryQueryService.findById(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID categoryId) {
        categoryManagementApplicationService.disable(categoryId);
    }



}
