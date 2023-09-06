package com.ensemble.entreprendre.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.User_;
@Service
public class IndividualAnalysisFilterService extends BaseFilter<User> {
    
    public Specification<User> addCriterias(IndividualAnalysisUserDtoFilter filter, Specification<User> specification){
        if (filter != null) {
            if ( filter.getQuery() != null) {
                specification = addQueryCriteria(filter, specification);
            }   
        }
        return specification; 
    }
    public Specification<User> addQueryCriteria(IndividualAnalysisUserDtoFilter filter, Specification<User> origin) {
        Specification<User> target = (root, criteriaQuery, criteriaBuilder) -> {
            if (!filter.getQuery().isEmpty()) {
                return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(
                            User_.NAME)),
                            "%" + filter.getQuery().toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(
                            User_.FIRST_NAME)),
                            "%" + filter.getQuery().toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(
                            User_.POSTAL_CODE)),
                            "%" + filter.getQuery().toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(
                            User_.INTERN_STATUS)),
                            "%" + filter.getQuery().toLowerCase() + "%"));
            }
        return criteriaBuilder.and();
        };
        return ensureSpecification(origin, target); 
    }
}
