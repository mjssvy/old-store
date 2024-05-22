package com.project.shopapp.service;

import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.services.product.image.ProductImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductImageServiceTest {

    @Mock
    private ProductImageRepository productImageRepository;


    @InjectMocks
    private ProductImageService productImageService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }


//    @Test
//    void testDeleteProductImage_Success() throws Exception {
//        // Given
//
//        long productImageId = 1L;
//        String imageUrl = "image-url";
//        ProductImage productImage = new ProductImage();
//
//        when(productImageRepository.findById(productImageId)).thenReturn(Optional.of("imageUrl"));
//
//        // When
//        ProductImage deletedProductImage = productImageService.deleteProductImage(productImageId);
//
//        // Then
//        assertNotNull(deletedProductImage);
//        assertEquals(imageUrl, deletedProductImage.getImageUrl());
//        verify(productImageRepository, times(1)).findById(productImageId);
//        verify(productImageRepository, times(1)).deleteById(productImageId);
//    }

    @Test
    void testDeleteProductImage_NotFound() {
        // Given
        long productImageId = 1L;

        when(productImageRepository.findById(productImageId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(DataNotFoundException.class, () -> productImageService.deleteProductImage(productImageId));
        verify(productImageRepository, times(1)).findById(productImageId);
        verify(productImageRepository, never()).deleteById(anyLong());
    }
}