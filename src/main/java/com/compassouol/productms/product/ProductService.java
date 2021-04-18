package com.compassouol.productms.product;

import com.compassouol.productms.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private static final String NO_PRODUCT_FOUND_MESSAGE = "No product with this id was found.";

    @Autowired
    private ProductRepository productRepository;

    public Product find(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, NO_PRODUCT_FOUND_MESSAGE));
    }

    public List<Product> list() {
        return (List<Product>) productRepository.findAll();
    }

    public Product create(Product product) {
        productRepository.save(product);

        log.info("Successfully created new product: {}", product);

        return product;
    }

    public Product update(String id, Product product) {
        existById(id);
        product.setId(id);
        productRepository.save(product);

        log.info("Successfully updated product: {}", product);

        return product;
    }

    public void delete(String id) {
        Product product = find(id);
        productRepository.delete(product);

        log.info("Successfully deleted product: {}", product);
    }

    private void existById(String id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NO_PRODUCT_FOUND_MESSAGE);
        }
    }
}
