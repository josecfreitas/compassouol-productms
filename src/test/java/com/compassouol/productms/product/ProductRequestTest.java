package com.compassouol.productms.product;

import com.compassouol.productms.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductRequestTest {

    @Test
    public void testConvertingToProduct() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setDescription("A random description");
        productRequest.setPrice(new BigDecimal("123.3454"));

        Product product = productRequest.convert();

        assertEquals(productRequest.getName(), product.getName());
        assertEquals(productRequest.getDescription(), product.getDescription());
        assertEquals(productRequest.getPrice(), product.getPrice());
    }
}
