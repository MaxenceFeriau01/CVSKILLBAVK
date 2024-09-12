package com.ensemble.entreprendre.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
