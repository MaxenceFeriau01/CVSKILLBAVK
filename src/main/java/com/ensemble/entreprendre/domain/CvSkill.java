package com.ensemble.entreprendre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cv_skill")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CvSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "USR_ID", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cv_skill_pole_atout",
            joinColumns = @JoinColumn(name = "cv_skill_id"),
            inverseJoinColumns = @JoinColumn(name = "pole_atout_id")
    )
    private Set<PoleAtout> poleAtouts = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "cv_skill_pole_interet",
            joinColumns = @JoinColumn(name = "cv_skill_id"),
            inverseJoinColumns = @JoinColumn(name = "pole_interet_id")
    )
    private Set<PoleInteret> poleInterets = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "cv_skill_pole_personnalite",
            joinColumns = @JoinColumn(name = "cv_skill_id"),
            inverseJoinColumns = @JoinColumn(name = "pole_personnalite_id")
    )
    private Set<PolePersonnaliteType> polePersonnalitestypes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "cv_skill_pole_personnalite_trait",
            joinColumns = @JoinColumn(name = "cv_skill_id"),
            inverseJoinColumns = @JoinColumn(name = "pole_personnalite_trait_id")
    )
    private Set<PolePersonnaliteTrait> polePersonnaliteTraits = new HashSet<>();

    @OneToOne(mappedBy = "cvSkill", cascade = CascadeType.ALL, orphanRemoval = true)
    private Photo photo;

    @ManyToMany
    @JoinTable(
            name = "cv_skill_pole_loisir_interet",
            joinColumns = @JoinColumn(name = "cv_skill_id"),
            inverseJoinColumns = @JoinColumn(name = "pole_loisir_interet_id")
    )
    private Set<PoleLoisirInteret> poleLoisirInterets = new HashSet<>();

}