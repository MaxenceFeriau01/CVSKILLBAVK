package com.ensemble.entreprendre.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PoleLoisirInteretDto {

    private Long id;
    private String name;
    private PoleLoisirInteretType type;

    public enum PoleLoisirInteretType {
        SPORT, INTERET
    }
}
