package com.compassouol.productms.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products/search")
public class ProductSearchController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponse> search(ProductSearchRequest productSearchRequest) {
        return new ProductListMapper<ProductResponse>(productService.list(productSearchRequest))
                .map(ProductResponse::new);
    }
}
