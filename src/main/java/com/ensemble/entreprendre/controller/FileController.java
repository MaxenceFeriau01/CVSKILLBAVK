package com.ensemble.entreprendre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.FileDbDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.service.IFileDbService;

@RequestMapping(path = "/api/files")
@RestController
public class FileController {

	@Autowired
	private IFileDbService fileService;

	//TODO check for security if you need to just allow the connected user to get his files
	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public FileDbDto getById(@PathVariable(name = "id") long id) throws ApiException {
		return this.fileService.getById(id);
	}

}
