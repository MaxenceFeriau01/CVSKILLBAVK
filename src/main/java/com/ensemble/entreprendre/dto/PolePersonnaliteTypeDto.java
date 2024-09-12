package com.ensemble.entreprendre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class PolePersonnaliteTypeDto {

    private Long id;
    private String personnaliteType;
    private String[] associatedTraits;
}
