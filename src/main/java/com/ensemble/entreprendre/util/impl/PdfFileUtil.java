package com.ensemble.entreprendre.util.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.util.IPdfFileUtil;

@Component
public class PdfFileUtil implements IPdfFileUtil{

    @Override
    public byte[] compressPdf(MultipartFile mpFile) throws ApiException {
        try {
            //TODO: compress pdf
            return mpFile.getBytes();
        } catch (Exception e) {
            throw new ApiException("Erreur lors du téléversement fichier", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
