package com.ensemble.entreprendre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.JobOfferDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.JobOfferDtoFilter;
import com.ensemble.entreprendre.service.IJobOfferService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping(path="/joboffers")
@RestController
@CrossOrigin("http://localhost:4200")
public class JobOfferController {

	@Autowired
	private IJobOfferService jobOfferService;
	
	@PostMapping(path = "/")
	@Secured({"ROLE_ADMIN"})
	public JobOfferDto create(JobOfferDto toCreate) throws ApiException{ 
		return this.jobOfferService.create(toCreate);
	}
	
	
	@ApiOperation(value = "JobOffer getAll endpoint", response = JobOfferDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
		value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
		@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
		value = "Number of records per page.", defaultValue = "20"),
		@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
		value = "Sorting criteria in the format: property(,asc|desc). " +
		"Default sort order is ascending. " +
		"Multiple sort criteria are supported.")
	})
	@GetMapping(path = "/")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public  Page<JobOfferDto> getAll(
			@ApiIgnore(
					"Ignored because swagger ui shows the wrong params, instead they are explained in the implicit params"
			) Pageable pageable,
			JobOfferDtoFilter filter)
	{ 
		return this.jobOfferService.getAll(pageable, filter);
	}
	
	@GetMapping(path = "/{id}/")
	@ResponseStatus(HttpStatus.OK)
	public  JobOfferDto getById(@PathVariable(name = "id") long id) throws ApiException{ 
		return this.jobOfferService.getById(id);
	}
}
