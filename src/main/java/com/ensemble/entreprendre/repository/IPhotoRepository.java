package com.ensemble.entreprendre.repository;

import com.ensemble.entreprendre.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPhotoRepository extends JpaRepository<Photo, Long> {

    /**
     * Trouve une photo par l'ID du CV Skill associé.
     * @param cvSkillId L'ID du CV Skill
     * @return Optional contenant la photo si elle existe
     */
    Optional<Photo> findByCvSkillId(Long cvSkillId);

    /**
     * Vérifie si une photo existe pour un CV Skill donné.
     * @param cvSkillId L'ID du CV Skill
     * @return true si une photo existe, false sinon
     */
    boolean existsByCvSkillId(Long cvSkillId);

    /**
     * Supprime la photo associée à un CV Skill.
     * @param cvSkillId L'ID du CV Skill
     */
    void deleteByCvSkillId(Long cvSkillId);
}
