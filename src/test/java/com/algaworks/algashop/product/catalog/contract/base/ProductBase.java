package com.algaworks.algashop.product.catalog.contract.base;

import com.algaworks.algashop.product.catalog.application.PageModel;
import com.algaworks.algashop.product.catalog.application.ResourceNotFoundException;
import com.algaworks.algashop.product.catalog.application.product.management.ProductInput;
import com.algaworks.algashop.product.catalog.application.product.management.ProductManagementApplicationService;
import com.algaworks.algashop.product.catalog.application.product.query.ProductDetailOutput;
import com.algaworks.algashop.product.catalog.application.product.query.ProductQueryService;
import com.algaworks.algashop.product.catalog.application.query.service.ProductDetailOutputTestDataBuilder;
import com.algaworks.algashop.product.catalog.presentation.ProductController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ProductController.class)
@ExtendWith(RestDocumentationExtension.class)
public class ProductBase {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private ProductQueryService productQueryService;

    @MockitoBean
    private ProductManagementApplicationService productManagementApplicationService;

    public static final UUID validProductId = UUID.fromString("fffe6ec2-7103-48b3-8e4f-3b58e43fb75a");

    public static final UUID invalidProductId = UUID.fromString("21651a12-b126-4213-ac21-19f66ff4642e");

    public static final UUID createdProductId = UUID.fromString("f7c6843f-465c-476d-9a9b-4783bde4dc5e");

    public static final UUID updatedProductId = UUID.fromString("a3927f81-5d33-4b0e-b2e4-3c1a7bba8d4f");

    public static final UUID updatedNotFoundProductId = UUID.fromString("c7e42a19-8b54-4c92-9d2a-1f8ef83a37e6");

    public static final UUID deletedNotFoundProductId = UUID.fromString("7a6f3c9b-2d8e-4f1a-b5e2-9c3d7f8a1b2e");



    @BeforeEach
    void setUp(RestDocumentationContextProvider documentationContextProvider) {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(documentationContextProvider)
                        .snippets().withTemplateFormat(TemplateFormats.asciidoctor())
                        .and().operationPreprocessors().withResponseDefaults(Preprocessors.prettyPrint())
                )
                .alwaysDo(MockMvcRestDocumentation.document("{ClassName}/{methodName}"))
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8).build());

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        mockValidProductFindById();
        mockFilterProducts();
        mockCreateProduct();
        mockInvalidProductFindById();
        mockUpdateProduct();
        mockUpdateNotFoundProduct();
        mockDeletedProduct();
        mockDeletedNotFoundProduct();
    }

    private void mockDeletedProduct() {
        Mockito.doNothing().when(productManagementApplicationService).disable(any(UUID.class));
    }

    private void mockDeletedNotFoundProduct(){

        Mockito.doThrow(new ResourceNotFoundException())
                .when(productManagementApplicationService)
                .disable(eq(deletedNotFoundProductId));
    }

    private void mockInvalidProductFindById() {
        when(productQueryService.findById(invalidProductId))
                .thenThrow(new ResourceNotFoundException());
    }

    private void mockCreateProduct(){
        when(productManagementApplicationService.create(any(ProductInput.class)))
                .thenReturn(createdProductId);

        when(productQueryService.findById(createdProductId))
                .thenReturn(ProductDetailOutputTestDataBuilder.aProduct().inStock(false).build());
    }

    private void mockUpdateProduct(){
        Mockito.doNothing().when(productManagementApplicationService).update(any(UUID.class), any(ProductInput.class));

        when(productQueryService.findById(updatedProductId))
                .thenReturn(ProductDetailOutputTestDataBuilder.aProduct().build());
    }

    private void mockUpdateNotFoundProduct(){

        Mockito.doThrow(new ResourceNotFoundException())
                .when(productManagementApplicationService)
                .update(eq(updatedNotFoundProductId), any(ProductInput.class));
    }

    private void mockFilterProducts() {
        when(productQueryService.filter(
                Mockito.anyInt(), Mockito.anyInt()))
                .then((answer) -> {
                    Integer size = answer.getArgument(0);
                    return PageModel.<ProductDetailOutput> builder()
                            .number(0)
                            .size(size)
                            .totalPages(1)
                            .totalElements(2)
                            .content(
                                    List.of(
                                         ProductDetailOutputTestDataBuilder.aProduct().build(),
                                         ProductDetailOutputTestDataBuilder.aProductAlt1().build()
                                    )
                            ).build();
        });
    }

    private void mockValidProductFindById() {
        when(productQueryService.findById(validProductId))
                .thenReturn(ProductDetailOutputTestDataBuilder.aProduct().id(validProductId).build());
    }

}
