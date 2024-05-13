package com.project.shopapp.controllers;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CountImageControllerTest {

    @Test
    public void testCountImagesInFolder() {
        // Prepare test data
        String folderPath = "E:/KTPM/old-store/shopapp-backend/fakeimages";
        File folder = new File(folderPath);

        // Perform the count
        int imageCount = CountImageController.countImagesInFolder(folder);

        // Verify the result
        assertEquals(3, imageCount);
    }

    public static boolean isImageFile(String fileName) {
        return false;
        // Implementation of the method
    }

    @Test
    public void testIsImageFileWithValidImageExtension() {
        // Prepare test data
        String fileName = "image.jpg";

        // Perform the check
        boolean isImageFile = CountImageController.isImageFile(fileName);

        // Verify the result
        assertTrue(isImageFile);
    }

    @Test
    public void testIsImageFileWithInvalidImageExtension() {
        // Prepare test data
        String fileName = "document.pdf";

        // Perform the check
        boolean isImageFile = CountImageController.isImageFile(fileName);

        // Verify the result
        assertFalse(isImageFile);
    }
}