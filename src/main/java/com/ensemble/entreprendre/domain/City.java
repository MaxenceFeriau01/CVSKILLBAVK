package com.ensemble.entreprendre.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CITY_ID")
    Long id;

    @Column(name = "CITY_NAME", nullable = false)
    String name;

    @Column(name = "CITY_POSTAL_CODE", nullable = false)
    String postalCode;

    @OneToMany(mappedBy = "city", orphanRemoval = true)
    Collection<Company> companies;

}
