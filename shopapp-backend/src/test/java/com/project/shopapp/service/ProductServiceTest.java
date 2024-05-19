package com.project.shopapp.service;


import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.responses.product.ProductResponse;
import com.project.shopapp.services.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository, categoryRepository);
    }

    @Test
    void testCreateProduct_Success() throws DataNotFoundException {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(100.0);
        productDTO.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Product createdProduct = productService.createProduct(productDTO);

        // Then
        assertNotNull(createdProduct);
        assertEquals(productDTO.getName(), createdProduct.getName());
        assertEquals(productDTO.getPrice(), createdProduct.getPrice());
        assertEquals(category, createdProduct.getCategory());
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void testCreateProduct_CategoryNotFound() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(100.0);
        productDTO.setCategoryId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DataNotFoundException.class, () -> productService.createProduct(productDTO));
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testGetProductById_Exists() throws Exception {
        // Given
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");

        when(productRepository.getDetailProduct(productId)).thenReturn(Optional.of(product));

        // When
        Product foundProduct = productService.getProductById(productId);

        // Then
        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        assertEquals(product.getName(), foundProduct.getName());
        verify(productRepository, times(1)).getDetailProduct(productId);
    }

    @Test
    void testGetProductById_NotExists() {
        // Given
        long productId = 1L;

        when(productRepository.getDetailProduct(productId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DataNotFoundException.class, () -> productService.getProductById(productId));
        verify(productRepository, times(1)).getDetailProduct(productId);
    }

    @Test
    void testFindProductsByIds() {
        // Given
        List<Long> productIds = Arrays.asList(1L, 2L, 3L);
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1"),
                new Product(2L, "Product 2"),
                new Product(3L, "Product 3")
        );

        when(productRepository.findProductsByIds(productIds)).thenReturn(products);

        // When
        List<Product> foundProducts = productService.findProductsByIds(productIds);

        // Then
        assertNotNull(foundProducts);
        assertEquals(products.size(), foundProducts.size());
        assertEquals(products, foundProducts);
        verify(productRepository, times(1)).findProductsByIds(productIds);
    }

    @Test
    void testGetAllProducts_Success() {
        // Given
        String keyword = "test";
        Long categoryId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Product> products = Collections.singletonList(new Product());
        Page<Product> productPage = new PageImpl<>(products);

        when(productRepository.searchProducts(categoryId, keyword, pageRequest)).thenReturn(productPage);

        // When
        Page<ProductResponse> result = productService.getAllProducts(keyword, categoryId, pageRequest);

        // Then
        assertNotNull(result);
        assertEquals(products.size(), result.getContent().size());
        verify(productRepository, times(1)).searchProducts(categoryId, keyword, pageRequest);
    }

    @Test
    void testGetAllProducts_NoResults() {
        // Given
        String keyword = "test";
        Long categoryId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(Collections.emptyList());

        when(productRepository.searchProducts(categoryId, keyword, pageRequest)).thenReturn(productPage);

        // When
        Page<ProductResponse> result = productService.getAllProducts(keyword, categoryId, pageRequest);

        // Then
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        verify(productRepository, times(1)).searchProducts(categoryId, keyword, pageRequest);
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        // Given
        long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Product");
        productDTO.setPrice(200.0);
        productDTO.setCategoryId(2L);

        Product existingProduct = new Product(productId, "Old Product");
        Category category = new Category();
        category.setId(2L);
        category.setName("Updated Category");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        // When
        Product updatedProduct = productService.updateProduct(productId, productDTO);

        // Then
        assertNotNull(updatedProduct);
        assertEquals(productDTO.getName(), updatedProduct.getName());
        assertEquals(productDTO.getPrice(), updatedProduct.getPrice());
        assertEquals(category, updatedProduct.getCategory());
        verify(productRepository, times(1)).findById(productId);
        verify(categoryRepository, times(1)).findById(2L);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        // Given
        long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Product");
        productDTO.setPrice(200.0);
        productDTO.setCategoryId(2L);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(DataNotFoundException.class, () -> productService.updateProduct(productId, productDTO));
        verify(productRepository, times(1)).findById(productId);
        verify(categoryRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void testDeleteProduct() {
        // Given
        long productId = 1L;
        Product product = new Product(productId, "Test Product");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        productService.deleteProduct(productId);

        // Then
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testExistsByName_Exists() {
        // Given
        String productName = "Test Product";

        when(productRepository.existsByName(productName)).thenReturn(true);

        // When
        boolean result = productService.existsByName(productName);

        // Then
        assertTrue(result);
        verify(productRepository, times(1)).existsByName(productName);
    }

    @Test
    void testExistsByName_NotExists() {
        // Given
        String productName = "Test Product";

        when(productRepository.existsByName(productName)).thenReturn(false);

        // When
        boolean result = productService.existsByName(productName);

        // Then
        assertFalse(result);
        verify(productRepository, times(1)).existsByName(productName);
    }
}
