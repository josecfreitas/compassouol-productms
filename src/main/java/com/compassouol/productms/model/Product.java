package com.compassouol.productms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id")
@Entity
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
