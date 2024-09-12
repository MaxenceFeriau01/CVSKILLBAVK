package com.ensemble.entreprendre.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ensemble.entreprendre.dto.EventLocationDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.EventLocationDtoFilter;

public interface IEventLocationService {
    Page<EventLocationDto> findAll(Pageable pageable, EventLocationDtoFilter filter) throws ApiException;

    EventLocationDto findById(Long id) throws ApiException;

    EventLocationDto createEventLocation(EventLocationDto toCreate) throws ApiException;

    EventLocationDto updateEventLocationById(Long id, EventLocationDto toUpdate) throws ApiException;

    EventLocationDto deleteEventLocationById(Long id) throws ApiException;
}
