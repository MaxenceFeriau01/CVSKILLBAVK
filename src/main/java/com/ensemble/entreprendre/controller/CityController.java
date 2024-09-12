package com.ensemble.entreprendre.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.CityDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.service.ICityService;

@RequestMapping(path = "/api/cities")
@RestController
public class CityController {

	@Autowired
	private ICityService cityService;

	@GetMapping
	public Collection<CityDto> getAll() throws ApiException {
		return this.cityService.findAll();
	}

}
