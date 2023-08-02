package com.ensemble.entreprendre.util;

import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.exception.ApiException;

public interface IPdfFileUtil {

    /**
     * 
     * @param mpFile to compress
     * @return byte array of the compressed file
     * @throws ApiException
     */
    public byte[] compressPdf(MultipartFile mpFile) throws ApiException;
}
