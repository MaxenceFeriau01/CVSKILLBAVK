package com.ensemble.entreprendre.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ensemble.entreprendre.util.impl.ImageFileUtil;

@ExtendWith(SpringExtension.class)
public class ImageFileUtilTest {

    private ImageFileUtil imageFileUtil = new ImageFileUtil();

    @Test
    public void testCompressImage() throws IOException {

        BufferedImage image = createComplexImage();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        byte[] imageData = outputStream.toByteArray();

        // Create a MockMultipartFile with image data
        MockMultipartFile file = new MockMultipartFile("testFile.jpg", "testFile.jpg", "image/jpeg", imageData);

        byte[] compressedImage = imageFileUtil.compressImage(file);

        assertNotNull(compressedImage);
        assertTrue(compressedImage.length < file.getSize());

    }

    @Test
    public void testCheckAcceptedExtensions() {

        String[] acceptedExtensions = { "jpg", "png", "gif" };

        assertTrue(imageFileUtil.checkAcceptedExtensions("test.jpg", acceptedExtensions));
        assertTrue(imageFileUtil.checkAcceptedExtensions("test.png", acceptedExtensions));
        assertFalse(imageFileUtil.checkAcceptedExtensions("test.txt", acceptedExtensions));
    }

    @Test
    public void testCheckAcceptedFileSize() {

        Double acceptedSize = 10.0;
        assertTrue(imageFileUtil.checkAcceptedFileSize(5.0 * 1024 * 1024, acceptedSize));
        assertTrue(imageFileUtil.checkAcceptedFileSize(10.0 * 1024 * 1024, acceptedSize));
        assertFalse(imageFileUtil.checkAcceptedFileSize(15.0 * 1024 * 1024, acceptedSize));
    }

    private BufferedImage createComplexImage() {
        int width = 800;
        int height = 600;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // Draw a color gradient
        GradientPaint gradient = new GradientPaint(0, 0, Color.RED, width, height, Color.BLUE);
        graphics.setPaint(gradient);
        graphics.fillRect(0, 0, width, height);

        // Draw patterns
        graphics.setColor(Color.WHITE);
        graphics.setStroke(new BasicStroke(2.0f));

        int numRectangles = 100;
        for (int i = 0; i < numRectangles; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            int rectWidth = (int) (Math.random() * 100);
            int rectHeight = (int) (Math.random() * 100);
            graphics.drawRect(x, y, rectWidth, rectHeight);
        }

        graphics.dispose();
        return image;
    }
}
