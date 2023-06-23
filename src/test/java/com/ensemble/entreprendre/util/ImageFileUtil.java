package com.ensemble.entreprendre.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ImageFileUtil {

    @Test()
    public void Should_ReturnTrue_When_StringIsSet() {

    }

    @Test()
    public void Should_ReturnFalse_When_StringIsBlank() {

    }

    @Test()
    public void Should_ReturnFalse_When_StringIsEmpty() {

    }

    @Test()
    public void Should_ReturnFalse_When_StringIsNull() {

    }

    private String createTestFile(String filename, int size) {
        try {
            // Create a temporary file with the specified name
            File file = File.createTempFile(filename, ".txt");

            // Création d'un flux de sortie pour écrire les données dans le fichier
            FileOutputStream fos = new FileOutputStream(file);

            // Create an output stream to write the data to the file
            byte[] data = new byte[size];
            fos.write(data);

            fos.close();

            return file.getAbsolutePath();
        } catch (IOException e) {
            // Error handling when creating the file
            e.printStackTrace();
            return null;
        }
    }

}
