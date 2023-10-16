package com.ensemble.entreprendre.filter;

import com.ensemble.entreprendre.domain.InternStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndividualAnalysisUserDtoFilter {
    private String firstName;
    private String name;
    private int postalCode;
    private InternStatus internStatus;
}
