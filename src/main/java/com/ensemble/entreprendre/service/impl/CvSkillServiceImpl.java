package com.ensemble.entreprendre.service.impl;

import com.ensemble.entreprendre.converter.GenericConverter;
import com.ensemble.entreprendre.domain.*;
import com.ensemble.entreprendre.dto.CvSkillDto;
import com.ensemble.entreprendre.dto.PhotoDto;
import com.ensemble.entreprendre.dto.PoleLoisirInteretDto;
import com.ensemble.entreprendre.dto.PolePersonnaliteTypeDto;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.repository.*;
import com.ensemble.entreprendre.service.ICvSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Base64;

@Service
@Transactional
public class CvSkillServiceImpl implements ICvSkillService {

    private final ICvSkillRepository cvSkillRepository;
    private final IUserRepository userRepository;
    private final IPoleAtoutRepository poleAtoutRepository;
    private final IPoleInteretRepository poleInteretRepository;
    private final IPolePersonnaliteTypeRepository polePersonnaliteTypeRepository;
    private final IPolePersonnaliteTraitRepository polePersonnaliteTraitRepository;
    private final GenericConverter<CvSkill, CvSkillDto> cvSkillResponseConverter;
    private final IPhotoRepository photoRepository;
    private final IPoleLoisirInteretRepository poleLoisirInteretRepository;

    private static final Pattern BASE64_PATTERN = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");

    private boolean isBase64(String str) {
        return BASE64_PATTERN.matcher(str).matches();
    }
    @Autowired
    public CvSkillServiceImpl(ICvSkillRepository cvSkillRepository,
                              IUserRepository userRepository,
                              IPoleAtoutRepository poleAtoutRepository,
                              IPoleInteretRepository poleInteretRepository,
                              IPolePersonnaliteTypeRepository polePersonnaliteTypeRepository,
                              IPoleLoisirInteretRepository poleLoisirInteretRepository,
                              IPolePersonnaliteTraitRepository polePersonnaliteTraitRepository,
                              GenericConverter<CvSkill, CvSkillDto> cvSkillResponseConverter,
                              IPhotoRepository photoRepository,
                              GenericConverter<Photo, PhotoDto> photoConverter) {
        this.cvSkillRepository = cvSkillRepository;
        this.userRepository = userRepository;
        this.poleAtoutRepository = poleAtoutRepository;
        this.poleLoisirInteretRepository = poleLoisirInteretRepository;
        this.poleInteretRepository = poleInteretRepository;
        this.polePersonnaliteTypeRepository = polePersonnaliteTypeRepository;
        this.polePersonnaliteTraitRepository = polePersonnaliteTraitRepository;
        this.cvSkillResponseConverter = cvSkillResponseConverter;
        this.photoRepository = photoRepository;

    }

    @Override
    public CvSkillDto createCvSkill(CvSkillDto cvSkillDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found" + userId));

        CvSkill cvSkill = cvSkillResponseConverter.dtoToEntity(cvSkillDto, CvSkill.class);
        cvSkill.setUser(user);


        Optional<CvSkill> userCvSkill = cvSkillRepository.findByUserId(userId);
        userCvSkill.ifPresent(skill -> cvSkill.setId(skill.getId()));



        Set<PoleInteret> updateInterets = new HashSet<>();
        for (PoleInteret interet : cvSkill.getPoleInterets()) {
            PoleInteret poleInteret = poleInteretRepository.findByInteret(interet.getInteret())
                    .orElse(new PoleInteret(interet.getInteret()));
            if (poleInteret.getId() == null) {
                poleInteret.setInteret(interet.getInteret());
                poleInteretRepository.save(poleInteret);
            }
            updateInterets.add(poleInteret);
        }
        cvSkill.setPoleInterets(updateInterets);



        Set<PoleLoisirInteret> updatedPoleLoisirInterets = new HashSet<>();
        if (cvSkillDto.getPoleLoisirInterets() != null) {
            for (PoleLoisirInteretDto pliDto : cvSkillDto.getPoleLoisirInterets()) {
                PoleLoisirInteret poleLoisirInteret = poleLoisirInteretRepository
                        .findByNameAndType(pliDto.getName(), PoleLoisirInteret.PoleLoisirInteretType.valueOf(pliDto.getType().name()))
                        .orElse(new PoleLoisirInteret(null, pliDto.getName(), PoleLoisirInteret.PoleLoisirInteretType.valueOf(pliDto.getType().name())));
                if (poleLoisirInteret.getId() == null) {
                    poleLoisirInteret = poleLoisirInteretRepository.save(poleLoisirInteret);
                }
                updatedPoleLoisirInterets.add(poleLoisirInteret);
            }
        }
        cvSkill.setPoleLoisirInterets(updatedPoleLoisirInterets);





        Set<PoleAtout> updatedAtouts = new HashSet<>();
        for (PoleAtout atout : cvSkill.getPoleAtouts()) {
            PoleAtout poleAtout = poleAtoutRepository.findByAtout(atout.getAtout())
                    .orElse(new PoleAtout(atout.getAtout()));
            if (poleAtout.getId() == null) {
                poleAtout.setAtout(atout.getAtout());
                poleAtoutRepository.save(poleAtout);
            }
            updatedAtouts.add(poleAtout);
        }
        cvSkill.setPoleAtouts(updatedAtouts);



        Set<PolePersonnaliteType> updatedPersonnalitesTypes = new HashSet<>();
        for (PolePersonnaliteType personnaliteType : cvSkill.getPolePersonnalitestypes()) {
            PolePersonnaliteType polePersonnaliteType = polePersonnaliteTypeRepository
                    .findByPersonnaliteType(personnaliteType.getPersonnaliteType())
                    .orElse(new PolePersonnaliteType(personnaliteType.getPersonnaliteType()));

            // Mettre à jour ou définir les traits associés
            polePersonnaliteType.setAssociatedTraits(personnaliteType.getAssociatedTraits());

            if (polePersonnaliteType.getId() == null) {
                polePersonnaliteType = polePersonnaliteTypeRepository.save(polePersonnaliteType);
            } else {
                polePersonnaliteType = polePersonnaliteTypeRepository.save(polePersonnaliteType); // Mise à jour des traits existants
            }
            updatedPersonnalitesTypes.add(polePersonnaliteType);
        }
        cvSkill.setPolePersonnalitestypes(updatedPersonnalitesTypes);



        Set<PolePersonnaliteTrait> updatedPersonnaliteTraits = new HashSet<>();
        for (PolePersonnaliteTrait personnaliteTrait : cvSkill.getPolePersonnaliteTraits()) {
            PolePersonnaliteTrait polePersonnaliteTrait = polePersonnaliteTraitRepository
                    .findByPersonnaliteTrait(personnaliteTrait.getPersonnaliteTrait())
                    .orElse(new PolePersonnaliteTrait(personnaliteTrait.getPersonnaliteTrait()));
            if (polePersonnaliteTrait.getId() == null) {
                polePersonnaliteTrait = polePersonnaliteTraitRepository.save(polePersonnaliteTrait);
            }
            updatedPersonnaliteTraits.add(polePersonnaliteTrait);
        }
        cvSkill.setPolePersonnaliteTraits(updatedPersonnaliteTraits);


        CvSkill savedCvSkill = cvSkillRepository.save(cvSkill);
        CvSkillDto savedCvSkillDto = cvSkillResponseConverter.entityToDto(savedCvSkill, CvSkillDto.class);
        savedCvSkillDto.setPhotoUrl("/api/cvskills/" + savedCvSkill.getId() + "/photo");
        return savedCvSkillDto;
    }



    @Override
    public List<CvSkillDto> getAllCvSkills() {
        List<CvSkill> cvSkills = cvSkillRepository.findAll();
        return cvSkills.stream()
                .map(cvSkill -> cvSkillResponseConverter.entityToDto(cvSkill, CvSkillDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CvSkillDto getCvSkillsByUserId(Long userId) throws ApiNotFoundException {
        CvSkill cvSkill = cvSkillRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiNotFoundException("Aucun CV skill trouvé pour l'utilisateur avec l'ID: " + userId));

        CvSkillDto cvSkillDto = cvSkillResponseConverter.entityToDto(cvSkill, CvSkillDto.class);
        cvSkillDto.setPhotoUrl("/api/cvskills/" + cvSkill.getId() + "/photo");
        return cvSkillDto;
    }

    @Override
    public Optional<CvSkillDto> getCvSkillById(Long id) {
        return cvSkillRepository.findById(id)
                .map(cvSkill -> cvSkillResponseConverter.entityToDto(cvSkill, CvSkillDto.class));
    }

    @Override
    public Optional<CvSkillDto> getCvSkillByIdAndUserId(Long id, Long userId) {
        return cvSkillRepository.findByIdAndUserId(id, userId)
                .map(cvSkill -> cvSkillResponseConverter.entityToDto(cvSkill, CvSkillDto.class));
    }

    @Override
    public CvSkillDto updateCvSkill(CvSkillDto cvSkillDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        CvSkill existingCvSkill = cvSkillRepository.findByUserId(userId)
                .orElseGet(() -> {
                    CvSkill newCvSkill = new CvSkill();
                    newCvSkill.setUser(user);
                    return newCvSkill;
                });


        // Mettre à jour les intérêts
        Set<PoleInteret> updatedInterets = new HashSet<>();
        for (PoleInteret interet : cvSkillDto.getPoleInterets()) {
            PoleInteret poleInteret = poleInteretRepository.findByInteret(interet.getInteret())
                    .orElse(new PoleInteret(interet.getInteret()));
            if (poleInteret.getId() == null) {
                poleInteret.setInteret(interet.getInteret());
                poleInteretRepository.save(poleInteret);
            }
            updatedInterets.add(poleInteret);
        }
        existingCvSkill.setPoleInterets(updatedInterets);

        // Mettre à jour les atouts
        Set<PoleAtout> updatedAtouts = new HashSet<>();
        for (PoleAtout atout : cvSkillDto.getPoleAtouts()) {
            PoleAtout poleAtout = poleAtoutRepository.findByAtout(atout.getAtout())
                    .orElse(new PoleAtout(atout.getAtout()));
            if (poleAtout.getId() == null) {
                poleAtout.setAtout(atout.getAtout());
                poleAtoutRepository.save(poleAtout);
            }
            updatedAtouts.add(poleAtout);
        }
        existingCvSkill.setPoleAtouts(updatedAtouts);

        // Mettre à jour les types de personnalité
        Set<PolePersonnaliteType> updatedPersonnalitesTypes = new HashSet<>();
        for (PolePersonnaliteTypeDto personnaliteTypeDto : cvSkillDto.getPolePersonnalitesTypes()) {
            PolePersonnaliteType polePersonnaliteType = convertToPolePersonnaliteType(personnaliteTypeDto);

            polePersonnaliteType = polePersonnaliteTypeRepository.findByPersonnaliteType(polePersonnaliteType.getPersonnaliteType())
                    .orElse(polePersonnaliteType);

            polePersonnaliteType.setAssociatedTraits(personnaliteTypeDto.getAssociatedTraits());

            polePersonnaliteType = polePersonnaliteTypeRepository.save(polePersonnaliteType);
            updatedPersonnalitesTypes.add(polePersonnaliteType);
        }
        existingCvSkill.setPolePersonnalitestypes(updatedPersonnalitesTypes);

        Set<PoleLoisirInteret> updatedPoleLoisirInterets = new HashSet<>();
        if (cvSkillDto.getPoleLoisirInterets() != null) {
            for (PoleLoisirInteretDto pliDto : cvSkillDto.getPoleLoisirInterets()) {
                PoleLoisirInteret poleLoisirInteret = poleLoisirInteretRepository
                        .findByNameAndType(pliDto.getName(), PoleLoisirInteret.PoleLoisirInteretType.valueOf(pliDto.getType().name()))
                        .orElse(new PoleLoisirInteret(null, pliDto.getName(), PoleLoisirInteret.PoleLoisirInteretType.valueOf(pliDto.getType().name())));
                if (poleLoisirInteret.getId() == null) {
                    poleLoisirInteret = poleLoisirInteretRepository.save(poleLoisirInteret);
                }
                updatedPoleLoisirInterets.add(poleLoisirInteret);
            }
        }
        existingCvSkill.setPoleLoisirInterets(updatedPoleLoisirInterets);


        // Mettre à jour les traits de personnalité
        Set<PolePersonnaliteTrait> updatedPersonnaliteTraits = new HashSet<>();
        for (PolePersonnaliteTrait personnaliteTrait : cvSkillDto.getPolePersonnaliteTraits()) {
            PolePersonnaliteTrait polePersonnaliteTrait = polePersonnaliteTraitRepository
                    .findByPersonnaliteTrait(personnaliteTrait.getPersonnaliteTrait())
                    .orElse(new PolePersonnaliteTrait(personnaliteTrait.getPersonnaliteTrait()));
            if (polePersonnaliteTrait.getId() == null) {
                polePersonnaliteTrait = polePersonnaliteTraitRepository.save(polePersonnaliteTrait);
            }
            updatedPersonnaliteTraits.add(polePersonnaliteTrait);
        }
        existingCvSkill.setPolePersonnaliteTraits(updatedPersonnaliteTraits);

        CvSkill updatedCvSkill = cvSkillRepository.save(existingCvSkill);
        CvSkillDto updatedCvSkillDto = cvSkillResponseConverter.entityToDto(updatedCvSkill, CvSkillDto.class);
        updatedCvSkillDto.setPhotoUrl("/api/cvskills/" + updatedCvSkill.getId() + "/photo");
        return updatedCvSkillDto;
    }
    private PolePersonnaliteType convertToPolePersonnaliteType(PolePersonnaliteTypeDto dto) {
        PolePersonnaliteType entity = new PolePersonnaliteType();
        entity.setPersonnaliteType(dto.getPersonnaliteType());
        entity.setAssociatedTraits(dto.getAssociatedTraits());
        return entity;
    }

    @Override
    public void deleteCvSkill(Long id) {
        cvSkillRepository.deleteById(id);
    }

    @Override
    public void deleteCvSkillByIdAndUserId(Long id, Long userId) {
        cvSkillRepository.deleteByIdAndUserId(id, userId);
    }


    @Override
    public CvSkillDto uploadPhoto(Long cvSkillId, MultipartFile file) throws ApiNotFoundException, IOException {
        CvSkill cvSkill = cvSkillRepository.findById(cvSkillId)
                .orElseThrow(() -> new ApiNotFoundException("CV Skill non trouvé avec l'ID: " + cvSkillId));

        Photo photo = cvSkill.getPhoto();
        if (photo == null) {
            photo = new Photo();
            photo.setCvSkill(cvSkill);
        }

        photo.setFileName(file.getOriginalFilename());
        photo.setFileType(file.getContentType());
        photo.setData(file.getBytes());

        photoRepository.save(photo);
        cvSkill.setPhoto(photo);

        CvSkill updatedCvSkill = cvSkillRepository.save(cvSkill);
        CvSkillDto updatedCvSkillDto = cvSkillResponseConverter.entityToDto(updatedCvSkill, CvSkillDto.class);
        updatedCvSkillDto.setPhotoUrl("/api/cvskills/" + updatedCvSkill.getId() + "/photo");
        return updatedCvSkillDto;
    }


    @Override
    public byte[] getPhotoData(Long cvSkillId) throws ApiNotFoundException {
        CvSkill cvSkill = cvSkillRepository.findById(cvSkillId)
                .orElseThrow(() -> new ApiNotFoundException("CV Skill non trouvé avec l'ID: " + cvSkillId));

        if (cvSkill.getPhoto() == null) {
            throw new ApiNotFoundException("Aucune photo trouvée pour le CV Skill avec l'ID: " + cvSkillId);
        }

        return cvSkill.getPhoto().getData();
    }

    @Override
    public void deletePhoto(Long cvSkillId) throws ApiNotFoundException {
        CvSkill cvSkill = cvSkillRepository.findById(cvSkillId)
                .orElseThrow(() -> new ApiNotFoundException("CV Skill non trouvé avec l'ID: " + cvSkillId));

        if (cvSkill.getPhoto() != null) {
            Photo photo = cvSkill.getPhoto();
            cvSkill.setPhoto(null);
            photoRepository.delete(photo);
            cvSkillRepository.save(cvSkill);
        } else {
            throw new ApiNotFoundException("Aucune photo trouvée pour le CV Skill avec l'ID: " + cvSkillId);
        }
    }
}