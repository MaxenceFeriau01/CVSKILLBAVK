package com.ensemble.entreprendre.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.dto.CompanyDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.CompanyDtoFilter;
import com.ensemble.entreprendre.service.ICompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping(path = "/companies")
@RestController
@CrossOrigin("*")
public class CompanyController {

	@Autowired
	private ICompanyService companyService;

	@Autowired
	ObjectMapper objectMapper;

	@PostMapping
	// TODO Remove ROLE_TEST
	@Secured({ "ROLE_ADMIN", "ROLE_TEST" })
	public CompanyDto create(@RequestPart("company") String company,
			@RequestPart(value = "logo", required = false) MultipartFile file) throws ApiException, IOException {
		CompanyDto toCreate = objectMapper.readValue(company, CompanyDto.class);
		if (file != null) {
			toCreate.setLogo(file.getBytes());
		}
		return this.companyService.create(toCreate);
	}

	@ApiOperation(value = "Company getAll endpoint", response = CompanyDto.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "20"), })
	@GetMapping
	public Page<CompanyDto> getAll(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, instead they are explained in the implicit params") @PageableDefault(sort = {
					"id" }, direction = Sort.Direction.ASC) Pageable pageable,
			CompanyDtoFilter filter) {
		// pageable.s
		return this.companyService.getAll(pageable, filter);
	}

	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CompanyDto getById(@PathVariable(name = "id") long id) throws ApiException {
		return this.companyService.getById(id);
	}

	@PutMapping(path = "/{id}")
	// TODO Remove ROLE_TEST
	@Secured({ "ROLE_ADMIN", "ROLE_TEST" })
	public CompanyDto update(@PathVariable(name = "id") long id, @RequestPart("company") String company,
			@RequestPart(value = "logo", required = false) MultipartFile file) throws ApiException, IOException {

		CompanyDto toUpdate = objectMapper.readValue(company, CompanyDto.class);
		if (file != null) {
			toUpdate.setLogo(file.getBytes());
		}
		return this.companyService.update(id, toUpdate);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	// TODO Remove ROLE_TEST
	@Secured({ "ROLE_ADMIN", "ROLE_TEST" })
	public CompanyDto delete(@PathVariable(name = "id") long id) throws ApiException {
		return this.companyService.delete(id);
	}
}
