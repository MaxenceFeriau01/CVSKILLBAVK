package com.ensemble.entreprendre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.domain.UserApplyCompany;
import com.ensemble.entreprendre.domain.UserApplyCompany_;
import com.ensemble.entreprendre.filter.StatPeriodDtoFilter;
import com.ensemble.entreprendre.repository.IUserApplyCompanyRepository;
import com.ensemble.entreprendre.service.IUserApplyService;

@Service
public class UserApplyServiceImpl implements IUserApplyService {

    @Autowired
    private IUserApplyCompanyRepository repository;

    @Override
    public Long countApplyingsWithPeriod(StatPeriodDtoFilter filter) {
        Specification<UserApplyCompany> specification = null;
        specification = addCountCriterias(filter, specification);
        if (specification == null) {
            return this.repository.count();
        }
        return this.repository.count(specification);
    }

    private Specification<UserApplyCompany> addCountCriterias(StatPeriodDtoFilter filter, Specification<UserApplyCompany> specification) {
        if (filter != null) {
            if (filter.getStartedAt() != null) {
                specification = addStartedAtCriteria(filter, specification);
            }
            if (filter.getEndedAt() != null) {
                specification = addEndedAtCriteria(filter, specification);
            }
        }
        return specification;
    }

    private Specification<UserApplyCompany> addStartedAtCriteria(StatPeriodDtoFilter filter, Specification<UserApplyCompany> origin) {
        Specification<UserApplyCompany> target = (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(UserApplyCompany_.CREATED_DATE), filter.getStartedAt());
        };
        return ensureSpecification(origin, target);
    }

    private Specification<UserApplyCompany> addEndedAtCriteria(StatPeriodDtoFilter filter, Specification<UserApplyCompany> origin) {
        Specification<UserApplyCompany> target = (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get(UserApplyCompany_.CREATED_DATE), filter.getEndedAt());
        };
        return ensureSpecification(origin, target);
    }

    private Specification<UserApplyCompany> ensureSpecification(Specification<UserApplyCompany> origin, Specification<UserApplyCompany> target) {
        if (origin == null)
            return target;
        if (target == null)
            return origin;
        return Specification.where(origin).and(target);
    }

}
