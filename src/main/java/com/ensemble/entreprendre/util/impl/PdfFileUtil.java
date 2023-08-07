package com.ensemble.entreprendre.util.impl;

import java.io.ByteArrayOutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.util.IPdfFileUtil;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

@Component
public class PdfFileUtil implements IPdfFileUtil{

    @Override
    public byte[] compressPdf(MultipartFile mpFile) throws ApiException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfReader reader = new PdfReader(mpFile.getBytes());
            PdfStamper stamper = new PdfStamper(reader, outputStream);
            stamper.setFullCompression();
            stamper.close();
            reader.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ApiException("Erreur lors du téléversement fichier", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean checkFileSize(Double fileSize, Double maxSize) {
        return fileSize <= maxSize;
    }
    
}
