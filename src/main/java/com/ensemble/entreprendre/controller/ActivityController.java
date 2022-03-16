package com.ensemble.entreprendre.controller;

import java.util.Collection;

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

import com.ensemble.entreprendre.dto.ActivityDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.ActivityDtoFilter;
import com.ensemble.entreprendre.projection.CustomActivity;
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
	@Secured({ "ROLE_ADMIN" })
	public ActivityDto create(@RequestBody ActivityDto toCreate) throws ApiException {
		return this.activityService.create(toCreate);
	}

	@ApiOperation(value = "search", response = ActivityDto.class)
	@Secured({ "ROLE_ADMIN" })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "20"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@GetMapping("/search")
	public Page<CustomActivity> search(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, instead they are explained in the implicit params") Pageable pageable,
			ActivityDtoFilter filter) throws ApiException {

		return this.activityService.getAllWithFilter(pageable, filter);
	}

	@GetMapping
	public Collection<ActivityDto> getAll() throws ApiException {

		return this.activityService.getAll();
	}

	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ActivityDto getById(@PathVariable(name = "id") long id) throws ApiException {

		return this.activityService.getById(id);
	}

	@PutMapping(path = "/{id}")
	@Secured({ "ROLE_ADMIN" })
	public ActivityDto update(@PathVariable(name = "id") long id, @RequestBody ActivityDto toUpdate)
			throws ApiException {
		return this.activityService.update(id, toUpdate);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@Secured({ "ROLE_ADMIN" })
	public ActivityDto delete(@PathVariable(name = "id") long id) throws ApiException {
		return this.activityService.delete(id);
	}
}
