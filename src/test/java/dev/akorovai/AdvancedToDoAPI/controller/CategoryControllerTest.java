package dev.akorovai.AdvancedToDoAPI.controller;

import dev.akorovai.AdvancedToDoAPI.category.CategoryController;
import dev.akorovai.AdvancedToDoAPI.category.CategoryDto;
import dev.akorovai.AdvancedToDoAPI.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void getAllCategories_shouldReturnListOfCategories() throws Exception {
        // Given
        List<CategoryDto> categories = new ArrayList<>();
        CategoryDto category1 = new CategoryDto();
        category1.setId(1L);
        CategoryDto category2 = new CategoryDto();
        category2.setId(2L);
        categories.add(category1);
        categories.add(category2);
        when(categoryService.getAllCategories()).thenReturn(categories);

        // When, Then
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }





    @Test
    void deleteCategoryById_shouldReturnNoContent() throws Exception {
        // Given
        Long categoryId = 1L;

        // When, Then
        mockMvc.perform(delete("/categories/{id}", categoryId))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteCategoryById(categoryId);
    }
}