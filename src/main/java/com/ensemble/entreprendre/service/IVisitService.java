package com.ensemble.entreprendre.service;

import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.StatPeriodDtoFilter;

public interface IVisitService {

    void addVisit() throws ApiException;

    Long countVisitsWithPeriod(StatPeriodDtoFilter filter);

}
