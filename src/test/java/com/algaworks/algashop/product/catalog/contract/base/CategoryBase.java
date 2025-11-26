package com.algaworks.algashop.product.catalog.contract.base;

import com.algaworks.algashop.product.catalog.application.ResourceNotFoundException;
import com.algaworks.algashop.product.catalog.application.category.management.CategoryInput;
import com.algaworks.algashop.product.catalog.application.category.management.CategoryManagementApplicationService;
import com.algaworks.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import com.algaworks.algashop.product.catalog.application.category.query.CategoryQueryService;
import com.algaworks.algashop.product.catalog.application.product.query.PageModel;
import com.algaworks.algashop.product.catalog.application.query.service.CategoryDetailOutputTestDataBuilder;
import com.algaworks.algashop.product.catalog.presentation.CategoryController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryBase {


    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private CategoryQueryService categoryQueryService;

    @MockitoBean
    private CategoryManagementApplicationService categoryManagementApplicationService;

    public static final UUID createdCategoryId = UUID.fromString("e3b27f9a-4c15-43d8-9a6e-7f1b92c4d8a3");

    public static final UUID validCategoryId = UUID.fromString("9f41b2e7-6c38-48d1-a9f5-2e7d3a1c4b69");

    public static final UUID invalidCategoryId = UUID.fromString("d6a3f9b2-18e4-4c75-9b1d-7e2a64c3f8d9");

    public static final UUID updatedCategoryId = UUID.fromString("4e9b1a73-2c56-4f8d-93a7-b2d5e61f0c48");

    public static final UUID updatedNotFoundCategoryId = UUID.fromString("b218f4c7-9d53-46a2-8e71-3fa9c2b6d4e8");

    public static final UUID deletedNotFoundCategoryId = UUID.fromString("f9c42e81-7a63-4d1b-b8e5-2a7f39c6d4b1");



    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8).build());

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        mockFilterCategories();
        mockCreateCategory();
        mockValidCategoryFindById();
        mockInvalidCategoryFindById();
        mockUpdateCategory();
        mockUpdateNotFoundCategory();
        mockDeletedCategory();
        mockDeletedNotFoundCategory();
    }

    private void mockCreateCategory(){
        when(categoryManagementApplicationService.create(Mockito.any(CategoryInput.class)))
                .thenReturn(createdCategoryId);

        when(categoryQueryService.findById(createdCategoryId))
                .thenReturn(CategoryDetailOutputTestDataBuilder.aCategory().build());
    }

    private void mockFilterCategories() {
        when(categoryQueryService.filter(
                        Mockito.anyInt(), Mockito.anyInt()))
                .then((answer) -> {
                    Integer size = answer.getArgument(0);
                    return PageModel.<CategoryDetailOutput> builder()
                            .number(0)
                            .size(size)
                            .totalPages(1)
                            .totalElements(2)
                            .content(
                                    List.of(
                                        CategoryDetailOutputTestDataBuilder.aCategory().build(),
                                        CategoryDetailOutputTestDataBuilder.aCategoryAlt1().build()
                                    )
                            ).build();

                });
    }
    private void mockValidCategoryFindById() {
        when(categoryQueryService.findById(validCategoryId))
                .thenReturn(CategoryDetailOutputTestDataBuilder.aCategory().id(validCategoryId).build());
    }

    private void mockInvalidCategoryFindById() {
        when(categoryQueryService.findById(invalidCategoryId))
                .thenThrow(new ResourceNotFoundException());
    }

    private void mockUpdateCategory(){
        Mockito.doNothing().when(categoryManagementApplicationService).update(any(UUID.class), any(CategoryInput.class));

        when(categoryQueryService.findById(updatedCategoryId))
                .thenReturn(CategoryDetailOutputTestDataBuilder.aCategory().build());
    }

    private void mockUpdateNotFoundCategory(){

        Mockito.doThrow(new ResourceNotFoundException())
                .when(categoryManagementApplicationService)
                .update(eq(updatedNotFoundCategoryId), any(CategoryInput.class));
    }

    private void mockDeletedCategory() {
        Mockito.doNothing().when(categoryManagementApplicationService).disable(any(UUID.class));
    }

    private void mockDeletedNotFoundCategory(){

        Mockito.doThrow(new ResourceNotFoundException())
                .when(categoryManagementApplicationService)
                .disable(eq(deletedNotFoundCategoryId));
    }







}
