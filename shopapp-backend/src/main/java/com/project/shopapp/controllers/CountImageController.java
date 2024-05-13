package com.project.shopapp.controllers;

import java.io.File;

// Remove the package declaration since it is not needed in this code block

public class CountImageController {
    public static void main(String[] args) {
        String folderPath = "E:/KTPM/old-store/shopapp-backend/fakeimages";
        File folder = new File(folderPath);
        
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            int imageCount = 0;
            
            for (File file : files) {
                if (file.isFile() && isImageFile(file.getName())) {
                    imageCount++;
                }
            }
            
            System.out.println("Number of images in folder: " + imageCount);
        } else {
            System.out.println("Invalid folder path");
        }
    }
    
    static boolean isImageFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif"};
        
        for (String imageExtension : imageExtensions) {
            if (extension.equalsIgnoreCase(imageExtension)) {
                return true;
            }
        }
        
        return false;
    }

    public static int countImagesInFolder(File folder) {
        throw new UnsupportedOperationException("Unimplemented method 'countImagesInFolder'");
    }
}
