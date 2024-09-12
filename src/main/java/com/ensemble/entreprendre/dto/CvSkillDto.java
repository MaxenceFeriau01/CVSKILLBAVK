package com.ensemble.entreprendre.dto;

import com.ensemble.entreprendre.domain.PoleAtout;
import com.ensemble.entreprendre.domain.PoleInteret;
import com.ensemble.entreprendre.domain.PolePersonnaliteTrait;
import com.ensemble.entreprendre.domain.PolePersonnaliteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CvSkillDto {

    private Long id;
    private UserCvSkillDto user;
    private Set<PoleAtout> poleAtouts;
    private Set<PoleInteret> poleInterets;
    private Set<PolePersonnaliteTypeDto> polePersonnalitesTypes;
    private Set<PolePersonnaliteTrait> polePersonnaliteTraits;
    private PhotoDto photo;
    private String photoUrl;
    private Set<PoleLoisirInteretDto> poleLoisirInterets;


}