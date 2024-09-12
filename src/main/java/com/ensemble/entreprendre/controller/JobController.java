package com.ensemble.entreprendre.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.JobAdministrationDto;
import com.ensemble.entreprendre.dto.JobDto;
import com.ensemble.entreprendre.dto.JobStatDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.JobDtoFilter;
import com.ensemble.entreprendre.service.IJobService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping(path = "/api/jobs")
@RestController
public class JobController {

	@Autowired
	private IJobService jobService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Secured({ "ROLE_ADMIN" })
	public JobDto create(@RequestBody JobDto toCreate) throws ApiException {
		return this.jobService.create(toCreate);
	}

	@ApiOperation(value = "Job getAll endpoint", response = JobDto.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "20"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@GetMapping("/search")
	@Secured({ "ROLE_ADMIN" })
	@ResponseStatus(HttpStatus.OK)
	public Page<JobAdministrationDto> getAll(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, instead they are explained in the implicit params") Pageable pageable,
			JobDtoFilter filter) throws ApiException {

		return this.jobService.getAllWithFilter(pageable, filter);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Collection<JobDto> getAll() throws ApiException {
		return this.jobService.getAll();
	}

	@Secured({ "ROLE_ADMIN" })
	@ApiOperation(value = "Job getJobStats endpoint", response = JobDto.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "20"),
	})
	@GetMapping("/stats")
	@ResponseStatus(HttpStatus.OK)
	public Page<JobStatDto> getJobStats(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, instead they are explained in the implicit params") Pageable pageable,
			JobDtoFilter filter) {
		return this.jobService.getJobStats(pageable, filter);
	}

	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public JobDto getById(@PathVariable(name = "id") long id) throws ApiException {
		return this.jobService.getById(id);
	}

	@PutMapping(path = "/{id}")
	@Secured({ "ROLE_ADMIN" })
	public JobDto update(@PathVariable(name = "id") long id, @RequestBody JobDto toUpdate) throws ApiException {
		return this.jobService.update(id, toUpdate);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Secured({ "ROLE_ADMIN" })
	public void delete(@PathVariable(name = "id") long id) throws ApiException {
		this.jobService.delete(id);
	}
}
