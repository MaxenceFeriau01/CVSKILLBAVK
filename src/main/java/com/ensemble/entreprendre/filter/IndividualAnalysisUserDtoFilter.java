package com.ensemble.entreprendre.filter;

import com.ensemble.entreprendre.domain.InternStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndividualAnalysisUserDtoFilter extends BasicDtoFilter {
    private String name;
    private String firstName;
    private int postalCode;
    private InternStatus internStatus;
}
