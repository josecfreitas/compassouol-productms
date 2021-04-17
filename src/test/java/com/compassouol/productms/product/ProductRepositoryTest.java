package com.compassouol.productms.product;

import com.compassouol.productms.factory.ProductFactory;
import com.compassouol.productms.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSavingProduct() {
        Product product = ProductFactory.createGenericProduct();

        productRepository.save(product);

        Product productFromDatabase = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(product, productFromDatabase);
    }

    @Test
    public void testScalePersistedOfPrice() {
        String price = "1000000000.123123";
        Product product = ProductFactory.createGenericProduct();
        product.setPrice(new BigDecimal(price));
        productRepository.save(product);

        Product productFromDatabase = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(
                new BigDecimal("1000000000.123123").setScale(2, RoundingMode.HALF_EVEN),
                productFromDatabase.getPrice()
        );
    }
}
