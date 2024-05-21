package com.project.shopapp.service;


import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.services.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryService categoryService;

    private CategoryDTO categoryDTO;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setName("Electronics");

        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
    }

    @Test
    void createCategory_Success() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category newCategory = categoryService.createCategory(categoryDTO);

        assertNotNull(newCategory);
        assertEquals(categoryDTO.getName(), newCategory.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void getCategoryById_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.getCategoryById(1L);

        assertNotNull(foundCategory);
        assertEquals(category.getName(), foundCategory.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void getCategoryById_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.getCategoryById(1L);
        });

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    void getAllCategories_Success() {
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));

        List<Category> categories = categoryService.getAllCategories();

        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertEquals(1, categories.size());
        verify(categoryRepository).findAll();
    }

    @Test
    void updateCategory_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category updatedCategory = categoryService.updateCategory(1L, categoryDTO);

        assertNotNull(updatedCategory);
        assertEquals(categoryDTO.getName(), updatedCategory.getName());
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void deleteCategory_Success() throws Exception {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findByCategory(category)).thenReturn(Collections.emptyList());

        Category deletedCategory = categoryService.deleteCategory(1L);

        assertNotNull(deletedCategory);
        assertEquals(category.getName(), deletedCategory.getName());
        verify(categoryRepository).findById(1L);
        verify(productRepository).findByCategory(category);
        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void deleteCategory_WithProducts() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findByCategory(category)).thenReturn(Collections.singletonList(new Product()));

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            categoryService.deleteCategory(1L);
        });

        assertEquals("Cannot delete category with associated products", exception.getMessage());
        verify(categoryRepository).findById(1L);
        verify(productRepository).findByCategory(category);
    }

    @Test
    void deleteCategory_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ChangeSetPersister.NotFoundException.class, () -> {
            categoryService.deleteCategory(1L);
        });

        verify(categoryRepository).findById(1L);
    }
}
