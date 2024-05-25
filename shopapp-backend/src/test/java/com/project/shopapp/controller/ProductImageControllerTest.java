package com.project.shopapp.controller;


import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.services.product.ProductService;
import com.project.shopapp.services.product.image.IProductImageService;
import com.project.shopapp.controllers.ProductImageController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductImageControllerTest {

    @InjectMocks
    private ProductImageController productImageController;

    @Mock
    private IProductImageService productImageService;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteProductImage_Success() throws Exception {
        // Arrange
        Long imageId = 1L;
        ProductImage productImage = new ProductImage();
        productImage.setId(imageId);
        productImage.setImageUrl("http://example.com/image.jpg");

        when(productImageService.deleteProductImage(imageId)).thenReturn(productImage);
        doNothing().when(productService).deleteFile(productImage.getImageUrl());

        // Act
        ResponseEntity<ResponseObject> response = (ResponseEntity<ResponseObject>) productImageController.delete(imageId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Delete product image successfully", response.getBody().getMessage());
        assertEquals(productImage, response.getBody().getData());

        verify(productImageService, times(1)).deleteProductImage(imageId);
        verify(productService, times(1)).deleteFile(productImage.getImageUrl());
    }

    @Test
    void testDeleteProductImage_NotFound() throws Exception {
        // Arrange
        Long imageId = 1L;

        when(productImageService.deleteProductImage(imageId)).thenReturn(null);

        // Act
        ResponseEntity<ResponseObject> response = (ResponseEntity<ResponseObject>) productImageController.delete(imageId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Delete product image successfully", response.getBody().getMessage());
        assertEquals(null, response.getBody().getData());

        verify(productImageService, times(1)).deleteProductImage(imageId);
        verify(productService, never()).deleteFile(anyString());
    }
}

