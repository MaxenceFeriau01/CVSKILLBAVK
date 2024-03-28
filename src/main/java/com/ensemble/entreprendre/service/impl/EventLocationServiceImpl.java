package com.ensemble.entreprendre.service.impl;

import javax.persistence.criteria.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.EventLocation;
import com.ensemble.entreprendre.domain.EventLocation_;
import com.ensemble.entreprendre.dto.EventLocationDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.filter.EventLocationDtoFilter;
import com.ensemble.entreprendre.repository.IEventLocationRepository;
import com.ensemble.entreprendre.service.IEventLocationService;
import com.ensemble.entreprendre.util.IStringUtil;

@Service
public class EventLocationServiceImpl implements IEventLocationService {

    @Autowired
    private IEventLocationRepository eventLocationRepository;

    @Autowired
    private GenericConverter<EventLocation, EventLocationDto> eventLocationConverter;

    @Autowired
    private IStringUtil stringUtil;

	@Override
	public Page<EventLocationDto> findAll(Pageable pageable, EventLocationDtoFilter filter) throws ApiException {
        Specification<EventLocation> specification = null;
        specification = addCriterias(filter, specification);
        return eventLocationConverter.entitiesToDtos(
            eventLocationRepository.findAll(specification, pageable),
            EventLocationDto.class
        );
	}

	@Override
	public EventLocationDto findById(Long id) throws ApiException {
        return eventLocationConverter.entityToDto(
            eventLocationRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Ce lieu d'événement n'existe pas.")),
            EventLocationDto.class
        );
	}

	@Override
	public EventLocationDto createEventLocation(EventLocationDto toCreate) throws ApiException {
        EventLocation toSave = eventLocationConverter.dtoToEntity(toCreate, EventLocation.class);
        return eventLocationConverter.entityToDto(
            eventLocationRepository.save(toSave),
            EventLocationDto.class
        );
	}

	@Override
	public EventLocationDto updateEventLocationById(Long id, EventLocationDto toUpdate) throws ApiException {
        EventLocation currentEventLocation = eventLocationRepository.findById(id)
            .orElseThrow(() -> new ApiNotFoundException("Ce lieu d'événement n'existe pas."));
        EventLocation toSave = eventLocationConverter.dtoToEntity(toUpdate, EventLocation.class);
        toSave.setId(currentEventLocation.getId());
        return eventLocationConverter.entityToDto(
            eventLocationRepository.save(toSave),
            EventLocationDto.class
        );
	}

	@Override
	public EventLocationDto deleteEventLocationById(Long id) throws ApiException {
        EventLocation eventLocation = eventLocationRepository.findById(id)
            .orElseThrow(() -> new ApiNotFoundException("Ce lieu d'événement n'existe pas."));
        eventLocationRepository.deleteById(eventLocation.getId());
        return eventLocationConverter.entityToDto(eventLocation, EventLocationDto.class);
	}

    private Specification<EventLocation> addCriterias(EventLocationDtoFilter filter, Specification<EventLocation> specification) {
        if (filter != null) {
            if (stringUtil.isValueSet(filter.getQuery())) {
                specification = addQueryCriteria(filter, specification);
            }
            if (stringUtil.isValueSet(filter.getSortField()) && stringUtil.isValueSet(filter.getSortType())) {
                specification = addSortingCriteria(filter, specification);
            }
        }
        return specification;
    }

    private Specification<EventLocation> ensureSpecification(Specification<EventLocation> origin, Specification<EventLocation> target) {
        if (origin == null)
            return target;
        if (target == null)
            return origin;
        return Specification.where(origin).and(target);
    }

    private Specification<EventLocation> addQueryCriteria(EventLocationDtoFilter filter, Specification<EventLocation> specification) {
        Specification<EventLocation> target = (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get(EventLocation_.NAME)), "%" + filter.getQuery().toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get(EventLocation_.ADDRESS)), "%" + filter.getQuery().toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get(EventLocation_.POSTAL_CODE)), "%" + filter.getQuery().toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get(EventLocation_.CITY)), "%" + filter.getQuery().toLowerCase() + "%")
            );
        };
        return ensureSpecification(specification, target);
    }

    private Specification<EventLocation> addSortingCriteria(EventLocationDtoFilter filter, Specification<EventLocation> specification) {
        Specification<EventLocation> target = (root, criteriaQuery, criteriaBuilder) -> {
            Path<Object> sortField = null;

            switch (filter.getSortField()) {
                case "id":
                    sortField = root.get(EventLocation_.ID);
                    break;
                case "name":
                    sortField = root.get(EventLocation_.NAME);
                    break;
                case "address":
                    sortField = root.get(EventLocation_.ADDRESS);
                    break;
                case "postalCode":
                    sortField = root.get(EventLocation_.POSTAL_CODE);
                    break;
                case "city":
                    sortField = root.get(EventLocation_.CITY);
                    break;
            }

            if (filter.getSortType().equalsIgnoreCase("asc")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(sortField));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(sortField));
            }

            return criteriaBuilder.and();
        };
        return ensureSpecification(specification, target);
    }
}
