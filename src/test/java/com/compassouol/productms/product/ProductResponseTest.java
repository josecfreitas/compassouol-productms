package com.compassouol.productms.product;

import com.compassouol.productms.factory.ProductFactory;
import com.compassouol.productms.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductResponseTest {

    @Test
    public void testMappingFromProduct() {
        Product product = ProductFactory.createGenericProduct();

        ProductResponse productResponse = new ProductResponse(product);

        assertEquals(product.getId(), productResponse.getId());
        assertEquals(product.getName(), productResponse.getName());
        assertEquals(product.getDescription(), productResponse.getDescription());
        assertEquals(product.getPrice(), productResponse.getPrice());
    }
}
