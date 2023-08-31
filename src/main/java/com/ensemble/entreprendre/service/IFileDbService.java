package com.ensemble.entreprendre.service;

import com.ensemble.entreprendre.dto.FileDbDto;
import com.ensemble.entreprendre.exception.ApiNotFoundException;

public interface IFileDbService {

	FileDbDto getById(Long id) throws ApiNotFoundException;

	void deleteFile(Long id) throws ApiNotFoundException;
}
