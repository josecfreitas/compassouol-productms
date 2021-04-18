package com.compassouol.productms.product;

import com.compassouol.productms.factory.ProductFactory;
import com.compassouol.productms.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProductService.class)
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Product genericProduct;

    @BeforeEach
    public void setUp() {
        genericProduct = ProductFactory.createGenericProduct();
    }

    @Test
    public void testFind_whenExistingId_thanReturnProduct() {
        genericProduct.setId("id");
        when(productRepository.findById(anyString())).thenReturn(Optional.of(genericProduct));

        Product product = productService.find("id");

        assertEquals(genericProduct, product);
    }

    @Test
    public void testFind_whenNotFoundId_thanThrowsResponseStatusException() {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.find("id"));
    }

    @Test
    public void testList_whenHasNoData_thanReturnEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<Product> productList = productService.list();

        assertTrue(productList.isEmpty());
    }


    @Test
    public void testList_whenHasData_thanReturnListOfAll() {
        List<Product> listOfThreeGenericProducts = List.of(ProductFactory.createGenericProduct(), ProductFactory.createGenericProduct(), ProductFactory.createGenericProduct());
        when(productRepository.findAll()).thenReturn(listOfThreeGenericProducts);

        List<Product> productList = productService.list();

        assertEquals(listOfThreeGenericProducts.size(), productList.size());
    }

    @Test
    public void testCreate_whenSuccessfullyCreates_thanReturnNewProduct() {
        when(productRepository.save(any())).thenReturn(genericProduct);

        Product product = productService.create(genericProduct);

        assertEquals(genericProduct, product);
    }

    @Test
    public void testUpdate_whenProductExists_thanReturnProduct() {
        String productId = "id";
        genericProduct.setId(productId);

        when(productRepository.existsById(anyString())).thenReturn(true);
        when(productRepository.save(any())).thenReturn(genericProduct);

        Product product = productService.update(productId, genericProduct);

        assertEquals(genericProduct, product);
    }

    @Test
    public void testUpdate_whenProductDontExists_thanThrowsResponseStatusException() {
        when(productRepository.existsById(anyString())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> productService.update("id", new Product()));
    }

    @Test
    public void testDelete_whenProductExists_thanDeleteProduct() {
        String productId = "id";
        genericProduct.setId(productId);

        when(productRepository.findById(anyString())).thenReturn(Optional.of(genericProduct));

        assertDoesNotThrow(() -> productService.delete(productId));
    }

    @Test
    public void testDelete_whenProductDontExists_thanThrowsResponseStatusException() {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.update("id", new Product()));
    }
}
