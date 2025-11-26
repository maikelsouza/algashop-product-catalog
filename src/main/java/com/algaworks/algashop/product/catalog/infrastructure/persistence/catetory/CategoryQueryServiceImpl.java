package com.algaworks.algashop.product.catalog.infrastructure.persistence.catetory;

import com.algaworks.algashop.product.catalog.application.PageModel;
import com.algaworks.algashop.product.catalog.application.ResourceNotFoundException;
import com.algaworks.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import com.algaworks.algashop.product.catalog.application.category.query.CategoryQueryService;
import com.algaworks.algashop.product.catalog.application.utility.Mapper;
import com.algaworks.algashop.product.catalog.domain.model.category.Category;
import com.algaworks.algashop.product.catalog.domain.model.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;

    private final Mapper mapper;

    @Override
    public CategoryDetailOutput findById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(ResourceNotFoundException::new);
        return mapper.convert(category,CategoryDetailOutput.class);
    }

    @Override
    public PageModel<CategoryDetailOutput> filter(Integer size, Integer page) {
        return null;
    }
}
