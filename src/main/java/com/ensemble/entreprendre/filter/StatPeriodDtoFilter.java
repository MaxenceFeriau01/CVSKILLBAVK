package com.ensemble.entreprendre.filter;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatPeriodDtoFilter {

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

}
