package com.ensemble.entreprendre.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventLocationDto {
    private Long id;
    private String name;
    private String address;
    private String postalCode;
    private String city;
}
