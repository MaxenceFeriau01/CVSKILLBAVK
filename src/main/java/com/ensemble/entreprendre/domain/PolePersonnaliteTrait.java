package com.ensemble.entreprendre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="pole_personnalite_trait")

public class PolePersonnaliteTrait {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String personnaliteTrait;

    public PolePersonnaliteTrait(String personnaliteTrait) {
        this.personnaliteTrait = personnaliteTrait;
    }


}
