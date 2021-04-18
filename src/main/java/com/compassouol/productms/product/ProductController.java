package com.compassouol.productms.product;

import com.compassouol.productms.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public ProductResponse find(@PathVariable String id) {
        return new ProductResponse(productService.find(id));
    }

    @GetMapping
    public List<ProductResponse> list() {
        return new ProductListMapper<ProductResponse>(productService.list())
                .map(ProductResponse::new);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest productRequest, UriComponentsBuilder uriComponentsBuilder) {
        Product product = productService.create(productRequest.convert());

        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .path("/products/{id}")
                                .buildAndExpand(product.getId()).toUri())
                .body(new ProductResponse(product));
    }

    @PutMapping("{id}")
    public ProductResponse update(@RequestBody @Valid ProductRequest productRequest, @PathVariable String id) {
        return new ProductResponse(productService.update(id, productRequest.convert()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
