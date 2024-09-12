package com.ensemble.entreprendre.service.impl;

import java.time.LocalDateTime;

import javax.persistence.criteria.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.Event;
import com.ensemble.entreprendre.domain.Event_;
import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.UserApplyEvent;
import com.ensemble.entreprendre.dto.EventDto;
import com.ensemble.entreprendre.exception.ApiAlreadyExistException;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.filter.EventDtoFilter;
import com.ensemble.entreprendre.repository.IEventRepository;
import com.ensemble.entreprendre.repository.IUserApplyEventRepository;
import com.ensemble.entreprendre.repository.IUserRepository;
import com.ensemble.entreprendre.service.IConnectedUserService;
import com.ensemble.entreprendre.service.IEventService;
import com.ensemble.entreprendre.util.IStringUtil;

@Service
public class EventServiceImpl implements IEventService {

    @Autowired
    private GenericConverter<Event, EventDto> eventConverter;

    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserApplyEventRepository userApplyEventRepository;

    @Autowired
    private IConnectedUserService connectedUserService;

    @Autowired
    private IStringUtil stringUtil;

    @Override
    public Page<EventDto> findAll(Pageable pageable, EventDtoFilter filter) throws ApiException {
        Specification<Event> specification = null;
        specification = addCriterias(filter, specification);
        return eventConverter.entitiesToDtos(
            eventRepository.findAll(specification, pageable),
            EventDto.class
        );
    }

    @Override
    public EventDto findById(Long id) throws ApiException {
        return eventConverter.entityToDto(
            eventRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Cet événement n'existe pas.")),
            EventDto.class
        );
    }

    @Override
    public EventDto createEvent(EventDto toCreate) throws ApiException {
        Event entityToSave = eventConverter.dtoToEntity(toCreate, Event.class);
        return eventConverter.entityToDto(
            eventRepository.save(entityToSave),
            EventDto.class
        );
    }

    @Override
    public EventDto updateEvent(Long id, EventDto toUpdate) throws ApiException {
        Event currentEvent = eventRepository.findById(id)
            .orElseThrow(() -> new ApiNotFoundException("Cet événement n'existe pas."));
        Event entityToSave = eventConverter.dtoToEntity(toUpdate, Event.class);
        entityToSave.setId(currentEvent.getId());
        return eventConverter.entityToDto(
            eventRepository.save(entityToSave),
            EventDto.class
        );
    }

    @Override
    public EventDto apply(Long id) throws ApiException {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ApiNotFoundException("Cet événement n'existe pas."));

        if (event.getEndedAt().isBefore(LocalDateTime.now())) {
            throw new ApiNotFoundException("Vous ne pouvez pas vous inscrire par un événement passé.");
        }

        User user = userRepository.findByEmail(connectedUserService.getConnectedUser().getUsername())
            .orElseThrow(() -> new ApiNotFoundException("Cet utilisateur n'existe pas."));

        if (event.getUserApplyings().stream().anyMatch((x) -> x.getUser().getId().equals(user.getId()))) {
            throw new ApiAlreadyExistException("Vous vous êtes déjà inscrit à cet événement.");
        }

        UserApplyEvent applyEvent = new UserApplyEvent();
        applyEvent.setUser(user);
        applyEvent.setEvent(event);
        applyEvent.setCreatedAt(LocalDateTime.now());
        userApplyEventRepository.save(applyEvent);
        event.getUserApplyings().add(applyEvent);

        return eventConverter.entityToDto(eventRepository.save(event), EventDto.class);
    }

    @Override
    public EventDto deleteEvent(Long id) throws ApiException {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ApiNotFoundException("Cet événement n'existe pas."));
        eventRepository.deleteById(event.getId());
        return eventConverter.entityToDto(event, EventDto.class);
    }

    private Specification<Event> addCriterias(EventDtoFilter filter, Specification<Event> specification) throws ApiException {
        if (filter != null) {
            if (stringUtil.isValueSet(filter.getQuery())) {
                specification = addQueryCriteria(filter, specification);
            }
            if (stringUtil.isValueSet(filter.getType())) {
                specification = addTypeCriteria(filter, specification);
            }
            if (filter.getActive() != null) {
                specification = addActiveCriteria(filter, specification);
            }
            if (!connectedUserService.isAdmin() || filter.getOnlyCurrentEvents()) {
                specification = addFutureEventCriteria(filter, specification);
            }
            if (stringUtil.isValueSet(filter.getSortField()) && stringUtil.isValueSet(filter.getSortType())) {
                specification = addSortingCriteria(filter, specification);
            }
        }
        return specification;
    }

    private Specification<Event> ensureSpecification(Specification<Event> origin, Specification<Event> target) {
        if (origin == null)
            return target;
        if (target == null)
            return origin;
        return Specification.where(origin).and(target);
    }

    private Specification<Event> addQueryCriteria(EventDtoFilter filter, Specification<Event> specification) {
        Specification<Event> target = (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.or(
                criteriaBuilder.like(root.get(Event_.NAME), "%" + filter.getQuery().toLowerCase() + "%"),
                criteriaBuilder.like(root.get(Event_.DESCRIPTION), "%" + filter.getQuery().toLowerCase() + "%")
            );
        };
        return ensureSpecification(specification, target);
    }

    private Specification<Event> addTypeCriteria(EventDtoFilter filter, Specification<Event> specification) {
        Specification<Event> target = (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Event_.TYPE), filter.getType());
        };
        return ensureSpecification(specification, target);
    }

    private Specification<Event> addActiveCriteria(EventDtoFilter filter, Specification<Event> specification) {
        Specification<Event> target = (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Event_.ACTIVE), filter.getActive());
        };
        return ensureSpecification(specification, target);
    }

    private Specification<Event> addFutureEventCriteria(EventDtoFilter filter, Specification<Event> specification) {
        Specification<Event> target = (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get(Event_.ENDED_AT), LocalDateTime.now());
        };
        return ensureSpecification(specification, target);
    }

    private Specification<Event> addSortingCriteria(EventDtoFilter filter, Specification<Event> origin) {
        Specification<Event> target = (root, criteriaQuery, criteriaBuilder) -> {
            Path<Object> sortField = null;

            switch (filter.getSortField()) {
                case "id":
                    sortField = root.get(Event_.ID);
                    break;
                default:
                case "active":
                    sortField = root.get(Event_.ACTIVE);
                    break;
                case "name":
                    sortField = root.get(Event_.NAME);
                    break;
                case "type":
                    sortField = root.get(Event_.TYPE);
                    break;
                case "startedAt":
                    sortField = root.get(Event_.STARTED_AT);
                    break;
                case "endedAt":
                    sortField = root.get(Event_.ENDED_AT);
                    break;
            }

            if (filter.getSortType().equalsIgnoreCase("asc")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(sortField));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(sortField));
            }

            return criteriaBuilder.and();
        };
        return ensureSpecification(origin, target);
    }
}
