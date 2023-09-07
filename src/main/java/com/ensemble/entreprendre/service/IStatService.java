package com.ensemble.entreprendre.service;

import com.ensemble.entreprendre.dto.StatGeneralDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.StatPeriodDtoFilter;

public interface IStatService {

    StatGeneralDto getGeneralStatistics(StatPeriodDtoFilter filter) throws ApiException;

}
