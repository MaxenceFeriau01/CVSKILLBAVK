package com.ensemble.entreprendre.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ensemble.entreprendre.domain.Activity;
import com.ensemble.entreprendre.dto.ActivityDto;
import com.ensemble.entreprendre.dto.ActivityAdministrationDto;

@Component
public class ActivityConverter extends GenericConverter<Activity, ActivityDto> {

	public List<ActivityAdministrationDto> mapTupleToActivityAdministrationDto(Page<Tuple> tuplePage) {
		return tuplePage.stream().map(tuple -> new ActivityAdministrationDto(
				tuple.get("id", Long.class),
				tuple.get("name", String.class),
				tuple.get("companyCount", Long.class),
				tuple.get("companySearchCount", Long.class))).collect(Collectors.toList());
	}

}
