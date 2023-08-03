package com.ensemble.entreprendre.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ensemble.entreprendre.domain.Activity;
import com.ensemble.entreprendre.dto.ActivityDto;
import com.ensemble.entreprendre.projection.CustomActivity;

@Component
public class ActivityConverter extends GenericConverter<Activity, ActivityDto> {

	public List<CustomActivity> mapTupleToCustomActivity(Page<Tuple> tuplePage) {
		return tuplePage.stream().map(tuple -> new CustomActivity(
				tuple.get("id", Long.class),
				tuple.get("name", String.class),
				tuple.get("companyCount", Long.class),
				tuple.get("companySearchCount", Long.class))).collect(Collectors.toList());
	}

}
