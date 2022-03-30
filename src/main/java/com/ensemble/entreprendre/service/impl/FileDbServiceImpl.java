package com.ensemble.entreprendre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.FileDb;
import com.ensemble.entreprendre.dto.FileDbDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.repository.IFileDbRepository;
import com.ensemble.entreprendre.service.IFileDbService;

@Service
public class FileDbServiceImpl implements IFileDbService {

	@Autowired
	IFileDbRepository fileDbRepository;

	@Autowired
	GenericConverter<FileDb, FileDbDto> fileDbConverter;

	@Override
	public FileDbDto getById(long id) throws ApiException {
		return this.fileDbConverter.entityToDto(this.fileDbRepository.findById(id)
				.orElseThrow(() -> new ApiNotFoundException("Ce fichier n'existe pas !")), FileDbDto.class);
	}

}
