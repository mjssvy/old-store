package com.project.shopapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopapp.responses.product.ProductResponse;
import com.project.shopapp.services.product.ProductRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductRedisServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @InjectMocks
    private ProductRedisService productRedisService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Set useRedisCache to false by default
        //productRedisService.setUseRedisCache(false);
    }

    @Test
    void testGetAllProducts_ReturnsNull_WhenUseRedisCacheIsFalse() throws JsonProcessingException {
        // Arrange
        when(productRedisService.getAllProducts(any(), any(), any())).thenCallRealMethod();

        // Act
        List<ProductResponse> result = productRedisService.getAllProducts("keyword", 1L, PageRequest.of(0, 10));

        // Assert
        assertEquals(null, result);
    }

//    @Test
//    void testSaveAllProducts() throws JsonProcessingException {
//        // Arrange
//        productRedisService.setUseRedisCache(true); // Set useRedisCache to true for this test
//        List<ProductResponse> productResponses = new ArrayList<>();
//        productResponses.add(new ProductResponse("Product 1", 1));
//        productResponses.add(new ProductResponse("Product 2", 2));
//
//        when(productRedisService.getKeyFrom(anyString(), anyLong(), any(PageRequest.class)))
//                .thenReturn("testKey");
//
//        // Act
//        productRedisService.saveAllProducts(productResponses, "keyword", 1L, PageRequest.of(0, 10));
//
//        // Assert
//        verify(redisTemplate, times(1)).opsForValue().set("testKey", "[{\"name\":\"Product 1\",\"id\":1},{\"name\":\"Product 2\",\"id\":2}]");
//    }
}