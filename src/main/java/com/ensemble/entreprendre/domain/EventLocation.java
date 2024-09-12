package com.ensemble.entreprendre.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EVENT_LOCATIONS")
public class EventLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_LOCATION_ID")
    private Long id;

    @Column(name = "EVENT_LOCATION_NAME", nullable = false)
    private String name;

    @Column(name = "EVENT_LOCATION_ADDRESS", nullable = false)
    private String address;

    @Column(name = "EVENT_LOCATION_POSTAL_CODE", nullable = false)
    private String postalCode;

    @Column(name = "EVENT_LOCATION_CITY", nullable = false)
    private String city;

    @OneToMany(mappedBy = "eventLocation")
    private Collection<Event> events;
}
