package com.project.shopapp.service;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.services.product.ProductService;
import com.project.shopapp.responses.product.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_Success() throws DataNotFoundException {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(100.0F);
        productDTO.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product createdProduct = productService.createProduct(productDTO);

        assertEquals("Test Product", createdProduct.getName());
        //assertEquals("Optional[100.0]", Optional.ofNullable(createdProduct.getPrice()));
        assertEquals(category, createdProduct.getCategory());
    }

    @Test
    void createProduct_CategoryNotFound() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> productService.createProduct(productDTO));
    }

    @Test
    void getProductById_Success() throws Exception {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.getDetailProduct(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);

        assertEquals(product, foundProduct);
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.getDetailProduct(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void findProductsByIds_Success() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productRepository.findProductsByIds(Arrays.asList(1L, 2L))).thenReturn(products);

        List<Product> foundProducts = productService.findProductsByIds(Arrays.asList(1L, 2L));

        assertEquals(2, foundProducts.size());
    }

    @Test
    void getAllProducts_Success() {
        Page<Product> productPage = new PageImpl<>(Arrays.asList(new Product(), new Product()));
        when(productRepository.searchProducts(any(), anyString(), any(PageRequest.class))).thenReturn(productPage);

        Page<ProductResponse> responsePage = productService.getAllProducts("keyword", 1L, PageRequest.of(0, 10));

        assertEquals(2, responsePage.getTotalElements());
    }

    @Test
    void updateProduct_Success() throws Exception {
        Product existingProduct = new Product();
        existingProduct.setId(1L);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(1L);
        productDTO.setName("Updated Product");

        Category category = new Category();
        category.setId(1L);

        when(productRepository.getDetailProduct(1L)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product updatedProduct = productService.updateProduct(1L, productDTO);

        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(category, updatedProduct.getCategory());
    }

    @Test
    void deleteProduct_Success() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void createProductImage_Success() throws Exception {
        Product product = new Product();
        product.setId(1L);

        ProductImageDTO productImageDTO = new ProductImageDTO();
        productImageDTO.setProductId(1L);
        productImageDTO.setImageUrl("http://image.url");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productImageRepository.findByProductId(1L)).thenReturn(Collections.emptyList());
        when(productImageRepository.save(any(ProductImage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductImage createdProductImage = productService.createProductImage(1L, productImageDTO);

        assertEquals("http://image.url", createdProductImage.getImageUrl());
        assertEquals(product, createdProductImage.getProduct());
    }

    @Test
    void createProductImage_ExceedsMaximumImages() {
        Product product = new Product();
        product.setId(1L);

        ProductImageDTO productImageDTO = new ProductImageDTO();
        productImageDTO.setProductId(1L);

        List<ProductImage> existingImages = Arrays.asList(new ProductImage(), new ProductImage(), new ProductImage(), new ProductImage(), new ProductImage());

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productImageRepository.findByProductId(1L)).thenReturn(existingImages);

        InvalidParamException invalidParamException = assertThrows(InvalidParamException.class, () -> productService.createProductImage(1L, productImageDTO));
    }


    @Test
    void deleteFile_Success() throws IOException {
        Path uploadDir = Paths.get("uploads");
        Path filePath = uploadDir.resolve("filename");

        try (var mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.exists(filePath)).thenReturn(true);
            mockedFiles.when(() -> Files.delete(filePath)).thenAnswer((Answer<Void>) invocation -> null);

            productService.deleteFile("filename");

            mockedFiles.verify(() -> Files.exists(filePath), times(1));
            mockedFiles.verify(() -> Files.delete(filePath), times(1));
        }
    }

    @Test
    void deleteFile_FileNotFound() {
        Path path = mock(Path.class);
        when(path.resolve(anyString())).thenReturn(path);
        when(Files.exists(path)).thenReturn(false);

        assertThrows(FileNotFoundException.class, () -> productService.deleteFile("filename"));
    }

    @Test
    void storeFile_Success() throws IOException {
        when(multipartFile.getContentType()).thenReturn("image/png");
        when(multipartFile.getOriginalFilename()).thenReturn("test.png");

        Path uploadDir = mock(Path.class);
        Path destination = mock(Path.class);

        when(Paths.get(anyString())).thenReturn(uploadDir);
        when(uploadDir.resolve(anyString())).thenReturn(destination);
        when(Files.exists(uploadDir)).thenReturn(false);

        doNothing().when(Files.class);
        Files.createDirectories(uploadDir);
        doNothing().when(Files.class);
        Files.copy(multipartFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        String filename = productService.storeFile(multipartFile);

        assertNotNull(filename);
        assertTrue(filename.contains("test.png"));
    }

    @Test
    void storeFile_InvalidFormat() {
        when(multipartFile.getContentType()).thenReturn("text/plain");

        assertThrows(IOException.class, () -> productService.storeFile(multipartFile));
    }
}
