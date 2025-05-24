package com.inside.dbmigrator.service;


import com.inside.dbmigrator.infrastructure.mysql.Revenue;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class RevenueSpecification {
    public Specification<Revenue> predicate(int StartId, int EndId, int Month) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();
            predicates = criteriaBuilder.and(predicates, criteriaBuilder.notEqual(root.get("status"), 0));
            if (StartId != 0) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.greaterThanOrEqualTo(root.get("id"), StartId));
            }
            if (EndId != 0) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.lessThanOrEqualTo(root.get("id"), EndId));
            }
            if (Month != 0) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("month"), Month));
            }
            return predicates;
        };
    }
}
