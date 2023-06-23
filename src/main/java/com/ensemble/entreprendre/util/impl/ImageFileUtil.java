package com.ensemble.entreprendre.util.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ensemble.entreprendre.util.IImageFileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageFileService implements IImageFileService {

    private static final String EXTENSION_INCOMPRESSIBLE_PNG = "png";

    public byte[] compressImage(MultipartFile mpFile) throws IOException {
        float quality = 0.7f;
        String imageName = mpFile.getOriginalFilename();
        String imageExtension = imageName.substring(imageName.lastIndexOf(".") + 1);

        // We do not compress png because if a image is PNG format it means that
        // compression
        // is done at that stage. PNG has a lossless compression algorithm
        if (imageExtension.equals(EXTENSION_INCOMPRESSIBLE_PNG)) {
            return mpFile.getBytes();
        }

        // Returns an Iterator containing all currently registered ImageWriters that
        // claim to be able to encode the named format.
        // You don't have to register one yourself; some are provided.
        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(imageExtension).next();
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // Check the api value that suites your
                                                                           // needs.
        // A compression quality setting of 0.0 is most generically interpreted as "high
        // compression is important,"
        // while a setting of 1.0 is most generically interpreted as "high image quality
        // is important."
        imageWriteParam.setCompressionQuality(quality);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // MemoryCacheImageOutputStream: An implementation of ImageOutputStream that
        // writes its output to a regular
        // OutputStream, i.e. the ByteArrayOutputStream.
        ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(baos);
        // Sets the destination to the given ImageOutputStream or other Object.
        imageWriter.setOutput(imageOutputStream);
        BufferedImage originalImage = null;
        try (InputStream inputStream = mpFile.getInputStream()) {
            originalImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            String info = String.format("compressImage - bufferedImage (file %s)- IOException - message: %s ",
                    imageName, e.getMessage());
            log.info(info);
            return baos.toByteArray();
        }
        IIOImage image = new IIOImage(originalImage, null, null);
        try {
            imageWriter.write(null, image, imageWriteParam);
        } catch (IOException e) {
            String info = String.format("compressImage - imageWriter (file %s)- IOException - message: %s ", imageName,
                    e.getMessage());
            log.info(info);
        } finally {
            imageWriter.dispose();
        }
        return baos.toByteArray();
    }

   
    public boolean checkAcceptedExtensions(String fileName, String[] acceptedExtensions) {
        String imageExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        for (String extension : acceptedExtensions) {
            if (imageExtension.equals(extension)) {
                return true;
            }
        }
        return false;
    }

  
    public boolean checkAcceptedFileSize(Double fileSize, Double acceptedSize) {
        return fileSize / 1024 / 1024 <= acceptedSize;
    }
}
