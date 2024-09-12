package com.ensemble.entreprendre.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pole_loisir_interet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PoleLoisirInteret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PoleLoisirInteretType type;

    @ManyToMany(mappedBy = "poleLoisirInterets")
    private Set<CvSkill> cvSkills = new HashSet<>();

    public enum PoleLoisirInteretType {
        SPORT, INTERET
    }

    public PoleLoisirInteret(Long id, String name, PoleLoisirInteretType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
