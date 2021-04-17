package com.compassouol.productms.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    public void testSetPriceCorrectScale() {
        String price = "1000000000.253123";

        Product product = new Product();
        product.setPrice(new BigDecimal(price));

        assertEquals(new BigDecimal(price).setScale(2, RoundingMode.HALF_EVEN), product.getPrice());
    }
}
