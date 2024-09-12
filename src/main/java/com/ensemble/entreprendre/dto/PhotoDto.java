package com.ensemble.entreprendre.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDto {

    private Long id;
    private String fileName;
    private String fileType;

    @JsonIgnore
    private byte[] data;

    // Constructeur sans données binaires
    public PhotoDto(Long id, String fileName, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    // Méthode pour obtenir les données en Base64
    public String getDataBase64() {
        return data != null ? Base64.getEncoder().encodeToString(data) : null;
    }
}