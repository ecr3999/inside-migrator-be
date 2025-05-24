package com.inside.dbmigrator.service;


import com.inside.dbmigrator.infrastructure.mysql.Revenue;
import com.inside.dbmigrator.infrastructure.mysql.Traffic;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TrafficSpecification {
    public Specification<Traffic> predicate(@Nullable Integer startId, @Nullable Integer endId, @Nullable Integer month) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();
            predicates = criteriaBuilder.and(predicates, criteriaBuilder.notEqual(root.get("month"), 0));
            if (startId != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.greaterThanOrEqualTo(root.get("id"), startId));
            }
            if (endId != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.lessThanOrEqualTo(root.get("id"), endId));
            }
            if (month != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("month"), month));
            }
            return predicates;
        };
    }
}
