package com.ensemble.entreprendre.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ensemble.entreprendre.dto.EventDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.EventDtoFilter;

public interface IEventService {
    Page<EventDto> findAll(Pageable pageable, EventDtoFilter filter) throws ApiException;

    EventDto findById(Long id) throws ApiException;

    EventDto createEvent(EventDto toCreate) throws ApiException;

    EventDto updateEvent(Long id, EventDto toUpdate) throws ApiException;

    EventDto apply(Long id) throws ApiException;

    EventDto deleteEvent(Long id) throws ApiException;
}
