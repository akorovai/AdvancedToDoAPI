package dev.akorovai.AdvancedToDoAPI.controller;

import dev.akorovai.AdvancedToDoAPI.dto.CategoryDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewCategoryDto;
import dev.akorovai.AdvancedToDoAPI.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test Category");
        when(categoryService.getAllCategories()).thenReturn(Collections.singletonList(categoryDto));

        // Act
        ResponseEntity<List<CategoryDto>> response = categoryController.getAllCategories();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Category", response.getBody().get(0).getName());
    }

    @Test
    void testAddNewCategory() {
        // Arrange
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("New Category");
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("New Category");
        when(categoryService.addNewCategory(any(NewCategoryDto.class))).thenReturn(categoryDto);

        // Act
        ResponseEntity<CategoryDto> response = categoryController.addNewCategory(newCategoryDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Category", response.getBody().getName());
    }

    @Test
    void testModifyCategoryById() throws Exception {
        // Arrange
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("Modified Category");
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Modified Category");
        when(categoryService.modifyCategory(anyLong(), any(NewCategoryDto.class))).thenReturn(categoryDto);

        // Act
        ResponseEntity<CategoryDto> response = categoryController.modifyCategoryById(1L, newCategoryDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Modified Category", response.getBody().getName());
    }

    @Test
    void testDeleteCategoryById() throws Exception {
        // Arrange
        doNothing().when(categoryService).deleteCategoryById(anyLong());

        // Act
        ResponseEntity<?> response = categoryController.deleteCategoryById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
