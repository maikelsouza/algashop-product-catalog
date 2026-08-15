package com.algaworks.algashop.product.catalog.presentation;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NonNull;

@Data
public class ProductQuantityModel {

    @NonNull
    @Min(1)
    private Integer quantity;


}
