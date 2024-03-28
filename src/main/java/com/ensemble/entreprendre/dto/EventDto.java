package com.ensemble.entreprendre.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto {
    private Long id;
    private String name;
    private Boolean active = false;
    private String type;
    private String image;
    private String description;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private EventLocationDto eventLocation;
}
