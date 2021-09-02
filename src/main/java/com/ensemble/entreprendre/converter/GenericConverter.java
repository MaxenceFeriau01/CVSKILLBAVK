package com.ensemble.entreprendre.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;



@Component
public class GenericConverter<E, D> {

	@Autowired
	private ModelMapper modelMapper;

	public D entityToDto(final E e, final Class<D> c) {
		return this.modelMapper.map(e, c);
	}

	public Collection<D> entitiesToDtos(final Collection<E> e, final Class<D> c) {
		if (e == null || e.isEmpty()) {
			return Collections.emptyList();
		}
		return e.stream().map(n -> this.entityToDto(n, c)).collect(Collectors.toCollection(ArrayList::new));
	}

	public Page<D> entitiesToDtos(final Page<E> e, final Class<D> c) {
		if (e == null || e.isEmpty()) {
			return Page.empty();
		}
		return e.map(n -> this.entityToDto(n, c));
	}

	public E dtoToEntity(final D dto, final Class<E> c) {
		return this.modelMapper.map(dto, c);
	}

	public Collection<E> dtosToEntities(final Collection<D> dto, final Class<E> c) {
		if (dto == null || dto.isEmpty()) {
			return Collections.emptyList();
		}
		return dto.stream().map(n -> this.dtoToEntity(n, c)).collect(Collectors.toCollection(ArrayList::new));
	}

}
