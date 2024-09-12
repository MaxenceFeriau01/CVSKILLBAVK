package com.ensemble.entreprendre.dto;

import com.ensemble.entreprendre.domain.enumeration.FileTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDbDto {

	private String id;

	private String name;

	private FileTypeEnum type;
	
	private byte[] data;
}
