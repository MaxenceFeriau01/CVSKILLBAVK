package com.ensemble.entreprendre.filter;

import org.springframework.data.jpa.domain.Specification;

public class BaseFilter<T> {
    protected Specification<T> ensureSpecification(Specification<T> origin,
            Specification<T> target) {
        if (origin == null) {
            return target;
        }
        if (target == null) {
            return origin;
        }
        return Specification.where(origin).and(target);
    }
}
