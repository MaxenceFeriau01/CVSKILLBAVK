package com.ensemble.entreprendre.service;

import com.ensemble.entreprendre.filter.StatPeriodDtoFilter;

public interface IInternTypeService {

    Long countOffersWithPeriod(StatPeriodDtoFilter filter);

}
