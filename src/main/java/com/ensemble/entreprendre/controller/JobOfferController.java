package com.ensemble.entreprendre.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.JobOfferDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.service.IJobOfferService;

@RequestMapping(path="/joboffers")
@RestController
@CrossOrigin("http://localhost:4200")
public class JobOfferController {

	@Autowired
	private IJobOfferService jobOfferService;
	
	@GetMapping(path = "/")
	public  Collection<JobOfferDto> getAll(){ 
		return this.jobOfferService.getAll();
	}
	
	@GetMapping(path = "/{id}/")
	@ResponseStatus(HttpStatus.OK)
	public  JobOfferDto getById(@PathVariable(name = "id") long id) throws ApiException{ 
		return this.jobOfferService.getById(id);
	}
}
