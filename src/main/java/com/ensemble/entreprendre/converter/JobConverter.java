package com.ensemble.entreprendre.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ensemble.entreprendre.domain.Job;
import com.ensemble.entreprendre.dto.JobDto;
import com.ensemble.entreprendre.projection.CustomJob;

@Component
public class JobConverter extends GenericConverter<Job, JobDto> {

    public List<CustomJob> mapTupleToCustomJob(Page<Tuple> tuplePage) {
        return tuplePage.stream().map(tuple -> new CustomJob(
                tuple.get("id", Long.class),
                tuple.get("name", String.class),
                tuple.get("companyCount", Long.class),
                tuple.get("userCount", Long.class))).collect(Collectors.toList());
    }

}
