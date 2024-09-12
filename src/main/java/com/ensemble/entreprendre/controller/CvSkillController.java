package com.ensemble.entreprendre.controller;

import com.ensemble.entreprendre.dto.CvSkillDto;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.service.ICvSkillService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/cvskills")
public class CvSkillController {

    private final ICvSkillService cvSkillService;

    @Autowired
    public CvSkillController(ICvSkillService cvSkillService) {
        this.cvSkillService = cvSkillService;
    }

    @PostMapping("/user/{userId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new CV Skill for a user", response = CvSkillDto.class)
    public ResponseEntity<CvSkillDto> createCvSkill(@RequestBody CvSkillDto cvSkillDto, @PathVariable Long userId) throws URISyntaxException {
        CvSkillDto createdCvSkill = cvSkillService.createCvSkill(cvSkillDto, userId);
        return ResponseEntity.created(new URI("/api/cvskills/" + createdCvSkill.getId()))
                .body(createdCvSkill);
    }


    @GetMapping
    @ApiOperation(value = "Get all CV Skills", response = List.class)
    public ResponseEntity<List<CvSkillDto>> getAllCvSkills() {
        List<CvSkillDto> cvSkills = cvSkillService.getAllCvSkills();
        return ResponseEntity.ok(cvSkills);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a CV Skill by its id", response = CvSkillDto.class)
    public ResponseEntity<CvSkillDto> getCvSkillById(@PathVariable Long id) {
        return cvSkillService.getCvSkillById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Get CV Skills for a specific user", response = CvSkillDto.class)
    public ResponseEntity<CvSkillDto> getCvSkillsByUserId(@PathVariable Long userId) throws ApiNotFoundException {
        CvSkillDto cvSkills = cvSkillService.getCvSkillsByUserId(userId);
        return ResponseEntity.ok(cvSkills);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ApiOperation(value = "Update a CV Skill", response = CvSkillDto.class)
    public ResponseEntity<CvSkillDto> updateCvSkill(@PathVariable Long id, @RequestBody CvSkillDto cvSkillDto, @RequestParam Long userId) {
        CvSkillDto updatedCvSkill = cvSkillService.updateCvSkill(cvSkillDto, userId);
        return ResponseEntity.ok(updatedCvSkill);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete a CV Skill", response = Void.class)
    public ResponseEntity<Void> deleteCvSkill(@PathVariable Long id) {
        cvSkillService.deleteCvSkill(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/user/{userId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete a CV Skill for a specific user", response = Void.class)
    public ResponseEntity<Void> deleteCvSkillByIdAndUserId(@PathVariable Long id, @PathVariable Long userId) {
        cvSkillService.deleteCvSkillByIdAndUserId(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cvSkillId}/photo")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<CvSkillDto> uploadPhoto(@PathVariable Long cvSkillId, @RequestParam("file") MultipartFile file) throws ApiNotFoundException, IOException {
        CvSkillDto updatedCvSkill = cvSkillService.uploadPhoto(cvSkillId, file);
        return ResponseEntity.ok(updatedCvSkill);
    }


    @GetMapping("/{cvSkillId}/photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long cvSkillId) throws ApiNotFoundException {
        byte[] photoData = cvSkillService.getPhotoData(cvSkillId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Ajustez selon le type d'image
                .body(photoData);
    }

    @DeleteMapping("/{cvSkillId}/photo")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePhoto(@PathVariable Long cvSkillId) throws ApiNotFoundException {
        cvSkillService.deletePhoto(cvSkillId);
        return ResponseEntity.noContent().build();
    }
}
