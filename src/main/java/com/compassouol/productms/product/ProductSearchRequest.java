package com.compassouol.productms.product;

import com.compassouol.productms.model.Product;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;

@Setter
public class ProductSearchRequest implements Specification<Product> {

    private String q;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        addQToPredicate(root, criteriaBuilder, predicates);
        addMinPriceToPredicate(root, criteriaBuilder, predicates);
        addMaxPriceToPredicate(root, criteriaBuilder, predicates);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    private void addQToPredicate(Root<Product> root, CriteriaBuilder criteriaBuilder, ArrayList<Predicate> predicates) {
        if (q != null) {
            predicates.add(
                    criteriaBuilder.or(
                            likePredicate(criteriaBuilder, root.get("name"), q),
                            likePredicate(criteriaBuilder, root.get("description"), q)
                    )
            );
        }
    }

    private void addMinPriceToPredicate(Root<Product> root, CriteriaBuilder criteriaBuilder, ArrayList<Predicate> predicates) {
        if (minPrice != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
    }

    private void addMaxPriceToPredicate(Root<Product> root, CriteriaBuilder criteriaBuilder, ArrayList<Predicate> predicates) {
        if (maxPrice != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
    }

    private Predicate likePredicate(CriteriaBuilder criteriaBuilder, Path<String> name, String value) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(name),
                String.format("%%%s%%", value.trim().toLowerCase().replaceAll("\\s", "%"))
        );
    }
}
