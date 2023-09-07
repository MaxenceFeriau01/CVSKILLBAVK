package com.ensemble.entreprendre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.dto.StatGeneralDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.StatPeriodDtoFilter;
import com.ensemble.entreprendre.service.IInternTypeService;
import com.ensemble.entreprendre.service.IStatService;
import com.ensemble.entreprendre.service.IUserApplyService;
import com.ensemble.entreprendre.service.IUserService;
import com.ensemble.entreprendre.service.IVisitService;

@Service
public class StatServiceImpl implements IStatService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IVisitService visitService;

    @Autowired
    private IUserApplyService userApplyService;

    @Autowired
    private IInternTypeService internTypeService;

    @Override
    public StatGeneralDto getGeneralStatistics(StatPeriodDtoFilter filter) throws ApiException {
        StatGeneralDto statGeneralDto = new StatGeneralDto();
        statGeneralDto.setNumbersUsers(userService.countUserSignUpsWithPeriod(filter));
        statGeneralDto.setNumbersVisits(visitService.countVisitsWithPeriod(filter));
        statGeneralDto.setNumbersApplyings(userApplyService.countApplyingsWithPeriod(filter));
        statGeneralDto.setNumbersOffers(internTypeService.countOffersWithPeriod(filter));
        return statGeneralDto;
    }

}
