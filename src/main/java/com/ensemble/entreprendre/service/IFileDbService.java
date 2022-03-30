package com.ensemble.entreprendre.service;

import com.ensemble.entreprendre.dto.FileDbDto;
import com.ensemble.entreprendre.exception.ApiException;

public interface IFileDbService {

	FileDbDto getById(long id) throws ApiException;
}
