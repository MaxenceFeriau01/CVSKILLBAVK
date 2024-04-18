package com.ensemble.entreprendre.domain;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Long id;

    @Column(name = "EVENT_NAME", nullable = false)
    private String name;

    @Column(name = "EVENT_ACTIVE", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private Boolean active = false;

    @Column(name = "EVENT_TYPE", nullable = false)
    private String type;

    @Column(name = "EVENT_IMAGE", columnDefinition = "TEXT", nullable = false)
    private String image;

    @Column(name = "EVENT_WEBSITE", nullable = true)
    private String website;

    @Column(name = "EVENT_DESCRIPTION", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(name = "EVENT_STARTED_AT", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "EVENT_ENDED_AT", nullable = false)
    private LocalDateTime endedAt;

    @ManyToOne
    @JoinColumn(name = "EVENT_LOCATION_ID", nullable = false)
    private EventLocation eventLocation;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Collection<UserApplyEvent> userApplyings;
}
