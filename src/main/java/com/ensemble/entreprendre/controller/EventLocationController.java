package com.ensemble.entreprendre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ensemble.entreprendre.dto.EventLocationDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.filter.EventLocationDtoFilter;
import com.ensemble.entreprendre.service.IEventLocationService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping(path = "/api/event-locations")
@RestController
public class EventLocationController {
    @Autowired
    private IEventLocationService eventLocationService;

    @ApiOperation(value = "Event findAll endpoint", response = EventLocationDto.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "20"), })
	@GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<EventLocationDto> findAll(@ApiIgnore Pageable pageable, EventLocationDtoFilter filter) throws ApiException {
        return eventLocationService.findAll(pageable, filter);
    }

    @ApiOperation(value = "Event findById endpoint", response = EventLocationDto.class)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventLocationDto findById(@PathVariable(name = "id") Long id) throws ApiException {
        return eventLocationService.findById(id);
    }

    @ApiOperation(value = "Event create endpoint", response = EventLocationDto.class)
    @PostMapping
    @Secured({ "ROLE_ADMIN" })
    @ResponseStatus(HttpStatus.CREATED)
    public EventLocationDto createEvent(@RequestBody EventLocationDto toCreate) throws ApiException {
        return eventLocationService.createEventLocation(toCreate);
    }

    @ApiOperation(value = "Event update endpoint", response = EventLocationDto.class)
    @PutMapping("/{id}")
    @Secured({ "ROLE_ADMIN" })
    @ResponseStatus(HttpStatus.OK)
    public EventLocationDto updateEvent(@PathVariable(name = "id") Long id, @RequestBody EventLocationDto toUpdate) throws ApiException {
        return eventLocationService.updateEventLocationById(id, toUpdate);
    }

    @ApiOperation(value = "Event delete endpoint", response = EventLocationDto.class)
    @DeleteMapping("/{id}")
    @Secured({ "ROLE_ADMIN" })
    @ResponseStatus(HttpStatus.OK)
    public EventLocationDto deleteEvent(@PathVariable(name = "id") Long id) throws ApiException {
        return eventLocationService.deleteEventLocationById(id);
    }
}
