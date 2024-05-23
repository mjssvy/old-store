package com.project.shopapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopapp.responses.product.ProductListResponse;
import com.project.shopapp.responses.product.ProductResponse;
import com.project.shopapp.services.product.ProductRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductRedisServiceTest {

    @InjectMocks
    private ProductRedisService productRedisService;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ObjectMapper redisObjectMapper;

    @Mock
    private RedisConnectionFactory redisConnectionFactory;

    @Mock
    private RedisConnection redisConnection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(productRedisService, "useRedisCache", true);

        when(redisTemplate.getConnectionFactory()).thenReturn(redisConnectionFactory);
        when(redisConnectionFactory.getConnection()).thenReturn(redisConnection);
    }

    @Test
    void getAllProducts_RedisCacheEnabled_ReturnsProducts() throws JsonProcessingException {
        String keyword = "test";
        Long categoryId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        String key = productRedisService.getKeyFrom(keyword, categoryId, pageRequest);
        String json = "{\"products\":[{\"id\":1,\"name\":\"Product 1\"}],\"totalPages\":1}";

        when(redisTemplate.opsForValue().get(key)).thenReturn(json);
        when(redisObjectMapper.readValue(json, ProductListResponse.class))
                .thenReturn(ProductListResponse.builder()
                        .products(List.of(ProductResponse.builder().id(1L).name("Product 1").build()))
                        .totalPages(1)
                        .build());

        ProductListResponse result = (ProductListResponse) productRedisService.getAllProducts(keyword, categoryId, pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getProducts().size());
        assertEquals(1L, result.getProducts().get(0).getId());
        assertEquals("Product 1", result.getProducts().get(0).getName());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void getAllProducts_RedisCacheDisabled_ReturnsNull() throws JsonProcessingException {
        ReflectionTestUtils.setField(productRedisService, "useRedisCache", false);

        ProductListResponse result = (ProductListResponse) productRedisService.getAllProducts("test", 1L, PageRequest.of(0, 10));

        assertNull(result);
    }

    @Test
    void clear_CallsFlushAll() {
        productRedisService.clear();

        verify(redisConnection, times(1)).flushAll();
    }

    @Test
    void saveAllProducts_Success() throws JsonProcessingException {
        String keyword = "test";
        Long categoryId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        String key = productRedisService.getKeyFrom(keyword, categoryId, pageRequest);
        ProductListResponse productListResponse = ProductListResponse.builder()
                .products(List.of(ProductResponse.builder().id(1L).name("Product 1").build()))
                .totalPages(1)
                .build();
        String json = "{\"products\":[{\"id\":1,\"name\":\"Product 1\"}],\"totalPages\":1}";

        when(redisObjectMapper.writeValueAsString(productListResponse)).thenReturn(json);

        productRedisService.saveAllProducts((List<ProductResponse>) productListResponse, keyword, categoryId, pageRequest);

        verify(redisTemplate.opsForValue(), times(1)).set(key, json);
    }
}