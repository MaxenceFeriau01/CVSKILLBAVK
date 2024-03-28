package com.ensemble.entreprendre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.EventDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.EventDtoFilter;
import com.ensemble.entreprendre.service.IEventService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.ResponseStatus;


@RequestMapping(path = "/api/events")
@RestController
public class EventController {
    @Autowired
    private IEventService eventService;

    @ApiOperation(value = "Event findAll endpoint", response = EventDto.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "20"), })
	@GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<EventDto> findAll(@ApiIgnore Pageable pageable, EventDtoFilter filter) throws ApiException {
        return eventService.findAll(pageable, filter);
    }

    @ApiOperation(value = "Event findById endpoint", response = EventDto.class)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto findById(@PathVariable(name = "id") Long id) throws ApiException {
        return eventService.findById(id);
    }

    @ApiOperation(value = "Event create endpoint", response = EventDto.class)
    @PostMapping
    @Secured({ "ROLE_ADMIN" })
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(@RequestBody EventDto toCreate) throws ApiException {
        return eventService.createEvent(toCreate);
    }

    @ApiOperation(value = "Event update endpoint", response = EventDto.class)
    @PutMapping("/{id}")
    @Secured({ "ROLE_ADMIN" })
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateEvent(@PathVariable(name = "id") Long id, @RequestBody EventDto toUpdate) throws ApiException {
        return eventService.updateEvent(id, toUpdate);
    }

    @ApiOperation(value = "User apply Event endpoint", response = EventDto.class)
    @PatchMapping("/{id}/apply")
    @ResponseStatus(HttpStatus.OK)
    public EventDto apply(@PathVariable(name = "id") Long id) throws ApiException {
        return eventService.apply(id);
    }

    @ApiOperation(value = "Event delete endpoint", response = EventDto.class)
    @DeleteMapping("/{id}")
    @Secured({ "ROLE_ADMIN" })
    @ResponseStatus(HttpStatus.OK)
    public EventDto deleteEvent(@PathVariable(name = "id") Long id) throws ApiException {
        return eventService.deleteEvent(id);
    }
}
