package com.ensemble.entreprendre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "pole_personnalite_type")
public class PolePersonnaliteType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String personnaliteType;


    @Type(type = "string-array")
    @Column(name = "associated_traits", columnDefinition = "text[]")
    private String[] associatedTraits;

    public PolePersonnaliteType(String personnaliteType) {
        this.personnaliteType = personnaliteType;
    }

}