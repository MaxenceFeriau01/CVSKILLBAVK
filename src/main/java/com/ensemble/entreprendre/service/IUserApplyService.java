package com.ensemble.entreprendre.service;

import com.ensemble.entreprendre.filter.StatPeriodDtoFilter;

public interface IUserApplyService {

    Long countApplyingsWithPeriod(StatPeriodDtoFilter filter);

}
