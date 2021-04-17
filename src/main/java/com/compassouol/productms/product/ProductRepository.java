package com.compassouol.productms.product;

import com.compassouol.productms.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {
}
