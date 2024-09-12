package com.ensemble.entreprendre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.filter.StatPeriodDtoFilter;
import com.ensemble.entreprendre.repository.IInternTypeRepository;
import com.ensemble.entreprendre.service.IInternTypeService;

@Service
public class InternTypeServiceImpl implements IInternTypeService {

    @Autowired
    private IInternTypeRepository repository;

    @Override
    public Long countOffersWithPeriod(StatPeriodDtoFilter filter) {
        return this.repository.countAllBetweenPeriod(filter.getStartedAt(), filter.getEndedAt());
    }

}
