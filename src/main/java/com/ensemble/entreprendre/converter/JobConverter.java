package com.ensemble.entreprendre.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ensemble.entreprendre.domain.Job;
import com.ensemble.entreprendre.dto.JobAdministrationDto;
import com.ensemble.entreprendre.dto.JobDto;
import com.ensemble.entreprendre.dto.JobStatDto;

@Component
public class JobConverter extends GenericConverter<Job, JobDto> {

    public List<JobAdministrationDto> mapTupleToJobAdministrationDto(Page<Tuple> tuplePage) {
        return tuplePage.stream().map(tuple -> new JobAdministrationDto(
                tuple.get("id", Long.class),
                tuple.get("name", String.class),
                tuple.get("companyCount", Long.class),
                tuple.get("userCount", Long.class))).collect(Collectors.toList());
    }

    public Page<JobStatDto> mapTupleToJobStatDto(Page<Tuple> tuplePage) {
        return tuplePage.map(tuple -> new JobStatDto(
                tuple.get("name", String.class),
                tuple.get("userCount", Long.class)
        ));
    }
}
