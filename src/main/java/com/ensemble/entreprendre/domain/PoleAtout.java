package com.ensemble.entreprendre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "pole_atout")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PoleAtout {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String atout;

    public PoleAtout(String atout) {
        this.atout = atout;
    }


}