package com.project.shopapp.service;


import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.services.product.image.ProductImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductImageServiceTest {

    @Mock
    private ProductImageRepository productImageRepository;

    @InjectMocks
    private ProductImageService productImageService;

    private ProductImage productImage;

    @BeforeEach
    void setUp() {
        productImage = new ProductImage();
        productImage.setId(1L);
        // Thiết lập các thuộc tính khác của productImage nếu cần
    }

    @Test
    void testDeleteProductImage_Success() throws Exception {
        // Arrange
        when(productImageRepository.findById(1L)).thenReturn(Optional.of(productImage));

        // Act
        ProductImage result = productImageService.deleteProductImage(1L);

        // Assert
        assertEquals(productImage, result);
        verify(productImageRepository, times(1)).findById(1L);
        verify(productImageRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProductImage_DataNotFound() {
        // Arrange
        when(productImageRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            productImageService.deleteProductImage(1L);
        });

        assertTrue(exception.getMessage().contains("Cannot find product image with id: 1"));
        verify(productImageRepository, times(1)).findById(1L);
        verify(productImageRepository, times(0)).deleteById(anyLong());
    }
}
