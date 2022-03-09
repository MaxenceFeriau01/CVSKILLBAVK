package com.ensemble.entreprendre.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.InternTypeDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.service.IInternTypeService;

@RequestMapping(path = "/api/internTypes")
@RestController
public class InternTypeController {

	@Autowired
	private IInternTypeService typeService;
	
	@GetMapping
	public Collection<InternTypeDto> getAll() throws ApiException {

		return this.typeService.findAll();
	}

	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public InternTypeDto getById(@PathVariable(name = "id") long id) throws ApiException {

		return this.typeService.findById(id);
	}
	
}
