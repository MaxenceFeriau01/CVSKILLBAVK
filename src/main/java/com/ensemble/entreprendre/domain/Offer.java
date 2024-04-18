package com.ensemble.entreprendre.domain;

import java.time.LocalDate;

import com.ensemble.entreprendre.domain.enumeration.OfferTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Offer {
    private Long id;
    private String title;
    private String city;
    private String reference;
    private LocalDate expiresAt;
    private String description;
    private LocalDate beginsAt;
    private String imageUrl;
    private String applyUrl;
    private OfferTypeEnum type;
    private Double longitude;
    private Double latitude;
}
