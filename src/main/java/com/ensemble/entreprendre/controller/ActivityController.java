package com.ensemble.entreprendre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.ActivityDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.service.IActivityService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping(path = "/api/activities")
@RestController
public class ActivityController {

	@Autowired
	private IActivityService activityService;

	@PostMapping
	// TODO Remove ROLE_TEST
	@Secured({ "ROLE_ADMIN", "ROLE_TEST" })
	public ActivityDto create(ActivityDto toCreate) throws ApiException {
		return this.activityService.create(toCreate);
	}

	@ApiOperation(value = "Activity getAll endpoint", response = ActivityDto.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "20"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@GetMapping
	public Page<ActivityDto> getAll(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, instead they are explained in the implicit params") Pageable pageable) throws ApiException {
		
		return this.activityService.getAll(pageable);
	}

	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ActivityDto getById(@PathVariable(name = "id") long id) throws ApiException {
		
		return this.activityService.getById(id);
	}

	@PutMapping(path = "/{id}")
	// TODO Remove ROLE_TEST
	@Secured({ "ROLE_ADMIN", "ROLE_TEST" })
	public ActivityDto update(@PathVariable(name = "id") long id, ActivityDto toUpdate) throws ApiException {
		return this.activityService.update(id, toUpdate);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	// TODO Remove ROLE_TEST
	@Secured({ "ROLE_ADMIN", "ROLE_TEST" })
	public ActivityDto delete(@PathVariable(name = "id") long id) throws ApiException {
		return this.activityService.delete(id);
	}
}
