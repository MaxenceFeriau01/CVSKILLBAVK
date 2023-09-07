package com.ensemble.entreprendre.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.StatGeneralDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.StatPeriodDtoFilter;
import com.ensemble.entreprendre.service.IStatService;

@RequestMapping(path = "/api/statistics")
@RestController
public class StatisticController {

    @Autowired
    private IStatService statService;

    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public StatGeneralDto getStatistics(@RequestParam String startedAt, String endedAt)
        throws ApiException {
        StatPeriodDtoFilter filter = new StatPeriodDtoFilter();
        filter.setStartedAt(LocalDateTime.parse(startedAt));
        filter.setEndedAt(LocalDateTime.parse(endedAt));
        return statService.getGeneralStatistics(filter);
    }

}
