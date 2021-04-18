package com.compassouol.productms.product;

import com.compassouol.productms.model.Product;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProductListMapper<T> {

    private final List<Product> productList;

    public List<T> map(Function<Product, T> mapper) {
        return productList
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
