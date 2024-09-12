package com.ensemble.entreprendre.service;

import com.ensemble.entreprendre.dto.CvSkillDto;
import com.ensemble.entreprendre.dto.PoleLoisirInteretDto;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ICvSkillService {
    CvSkillDto createCvSkill(CvSkillDto cvSkillDto, Long userId);
    List<CvSkillDto> getAllCvSkills();
    CvSkillDto getCvSkillsByUserId(Long userId) throws ApiNotFoundException;
    Optional<CvSkillDto> getCvSkillById(Long id);
    Optional<CvSkillDto> getCvSkillByIdAndUserId(Long id, Long userId);
    CvSkillDto updateCvSkill(CvSkillDto cvSkillDto, Long userId);
    void deleteCvSkill(Long id);
    void deleteCvSkillByIdAndUserId(Long id, Long userId);
    byte[] getPhotoData(Long cvSkillId) throws ApiNotFoundException;
    CvSkillDto uploadPhoto(Long cvSkillId, MultipartFile file) throws ApiNotFoundException, IOException;
    void deletePhoto(Long cvSkillId) throws ApiNotFoundException;
}