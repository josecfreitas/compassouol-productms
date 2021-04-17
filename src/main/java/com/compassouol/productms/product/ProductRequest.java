package com.compassouol.productms.product;

import com.compassouol.productms.factory.ModelMapperFactory;
import com.compassouol.productms.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class ProductRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Product convert() {
        Product product = new Product();
        ModelMapperFactory.getModelMapperStrict().map(this, product);
        return product;
    }
}
