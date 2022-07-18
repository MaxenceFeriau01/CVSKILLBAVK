package com.ensemble.entreprendre.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.InternStatusDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.service.IInternStatusService;

@RequestMapping(path = "/api/intern-status")
@RestController
public class InternStatusController {

	@Autowired
	private IInternStatusService statusService;

	@GetMapping
	public Collection<InternStatusDto> getAll() throws ApiException {
		return this.statusService.findAll();
	}

	@GetMapping(path = "/for-applicant")
	public Collection<InternStatusDto> getAllForApplicant() throws ApiException {
		return this.statusService.findAllForApplicant();
	}

	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public InternStatusDto getById(@PathVariable(name = "id") long id) throws ApiException {
		return this.statusService.findById(id);
	}

}
