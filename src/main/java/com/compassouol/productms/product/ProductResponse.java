package com.compassouol.productms.product;

import com.compassouol.productms.factory.ModelMapperFactory;
import com.compassouol.productms.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;

    public ProductResponse(Product product) {
        ModelMapperFactory.getModelMapperStrict().map(product, this);
    }
}
