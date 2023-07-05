package com.ensemble.entreprendre.util;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IImageFileUtil {


    /**
     * 
     * @param mpFile to compress
     * @return byte array of the compressed file
     * @throws IOException
     */
    public byte[] compressImage(MultipartFile mpFile) throws IOException;

     /**
     * @param fileName           file name
     * @param acceptedExtensions accepted extensions
     * @return true if the file is the same type as one of the accepted formats,
     *         false otherwise
     */
    public boolean checkAcceptedExtensions(String fileName, String[] acceptedExtensions);

    /**
     * @param fileSize     the size of the file in long
     * @param acceptedSize the accepted size of the file in MB
     * @return false if bigger that the accepted size, true otherwise
     */
    public boolean checkAcceptedFileSize(Double fileSize, Double acceptedSize);

}
