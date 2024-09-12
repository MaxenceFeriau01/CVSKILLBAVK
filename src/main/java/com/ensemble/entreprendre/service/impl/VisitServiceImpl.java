package com.ensemble.entreprendre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.Visit;
import com.ensemble.entreprendre.domain.Visit_;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.filter.StatPeriodDtoFilter;
import com.ensemble.entreprendre.repository.IUserRepository;
import com.ensemble.entreprendre.repository.IVisitRepository;
import com.ensemble.entreprendre.service.IVisitService;

@Service
public class VisitServiceImpl implements IVisitService {

    @Value("${connected.in.anonymous.value}")
    private String connectedInAnonymousValue;

    @Autowired
    private IVisitRepository visitRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void addVisit() throws ApiException {
        User user = null;
        UserDetails connectedUser = getConnectedUser();
        if (connectedUser != null) {
            user = userRepository.findByEmail(connectedUser.getUsername())
                .orElseThrow(() -> new ApiNotFoundException("User not found"));
        }
        Visit newVisit = new Visit();
        newVisit.setUser(user);
        visitRepository.save(newVisit);
    }

    private UserDetails getConnectedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal.equals(connectedInAnonymousValue)) {
            return null;
        }
        return (UserDetails) principal;
    }

    @Override
    public Long countVisitsWithPeriod(StatPeriodDtoFilter filter) {
        Specification<Visit> specification = null;
        specification = addCountCriterias(filter, specification);
        if (specification == null) {
            return this.visitRepository.count();
        }
        return this.visitRepository.count(specification);
    }

    private Specification<Visit> addCountCriterias(StatPeriodDtoFilter filter, Specification<Visit> specification) {
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

    private Specification<Visit> addStartedAtCriteria(StatPeriodDtoFilter filter, Specification<Visit> origin) {
        Specification<Visit> target = (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(Visit_.CREATED_DATE), filter.getStartedAt());
        };
        return ensureSpecification(origin, target);
    }

    private Specification<Visit> addEndedAtCriteria(StatPeriodDtoFilter filter, Specification<Visit> origin) {
        Specification<Visit> target = (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get(Visit_.CREATED_DATE), filter.getEndedAt());
        };
        return ensureSpecification(origin, target);
    }

    private Specification<Visit> ensureSpecification(Specification<Visit> origin, Specification<Visit> target) {
        if (origin == null)
            return target;
        if (target == null)
            return origin;
        return Specification.where(origin).and(target);
    }
}
