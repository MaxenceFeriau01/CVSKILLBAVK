package com.ensemble.entreprendre.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USERS_APPLIED_EVENTS")
public class UserApplyEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_EVENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USR_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "EVT_ID", nullable = false)
    private Event event;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
}
