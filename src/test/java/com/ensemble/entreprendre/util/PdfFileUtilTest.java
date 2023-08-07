package com.ensemble.entreprendre.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ensemble.entreprendre.util.impl.PdfFileUtil;

@ExtendWith(SpringExtension.class)
public class PdfFileUtilTest {

    private PdfFileUtil pdfFileUtil = new PdfFileUtil();

    @Test
    public void testCheckSizeBeforeCompression() {
        Double maxSize = 5.0;
        assertTrue(pdfFileUtil.checkFileSize(5.0, maxSize));
        assertTrue(pdfFileUtil.checkFileSize(4.0, maxSize));
        assertFalse(pdfFileUtil.checkFileSize(6.0, maxSize));
    }
}
