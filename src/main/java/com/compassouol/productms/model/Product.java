package com.compassouol.productms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Product {

    @Id
    private String id = UUID.randomUUID().toString();
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(scale = 2, nullable = false)
    private BigDecimal price;

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }
}
