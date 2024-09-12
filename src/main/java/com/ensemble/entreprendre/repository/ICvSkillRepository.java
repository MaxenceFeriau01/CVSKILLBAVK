package com.ensemble.entreprendre.repository;

import com.ensemble.entreprendre.domain.CvSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICvSkillRepository extends JpaRepository<CvSkill, Long> {
    Optional<CvSkill> findByUserId(Long userId);
    Optional<CvSkill> findByIdAndUserId(Long id, Long userId);
    void deleteByIdAndUserId(Long id, Long userId);
}