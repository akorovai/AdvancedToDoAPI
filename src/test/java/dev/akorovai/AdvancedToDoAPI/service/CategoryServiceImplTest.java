package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.CategoryDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewCategoryDto;
import dev.akorovai.AdvancedToDoAPI.entity.Category;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.CategoryNotFoundException;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.DuplicateCategoryException;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.EmptyCategoryListException;
import dev.akorovai.AdvancedToDoAPI.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImpl underTest;

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
    void getAllCategories_shouldReturnListOfCategories() throws EmptyCategoryListException {
        // Given
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);
        when(modelMapper.map(any(), eq(CategoryDto.class))).thenReturn(new CategoryDto());

        // When
        List<CategoryDto> result = underTest.getAllCategories();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void getAllCategories_shouldThrowEmptyCategoryListException_whenNoCategoriesFound() {
        // Given
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // When, Then
        assertThrows(EmptyCategoryListException.class, () -> underTest.getAllCategories());
    }

    @Test
    void addNewCategory_shouldReturnCategoryDto_whenCategoryDoesNotExist() throws DuplicateCategoryException {
        // Given
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("New Category");

        Category savedCategory = Category.builder().id(1L).name(newCategoryDto.getName()).build();
        CategoryDto categoryDto = new CategoryDto();

        when(categoryRepository.existsByName(newCategoryDto.getName())).thenReturn(false);
        when(categoryRepository.save(any())).thenReturn(savedCategory);
        when(modelMapper.map(newCategoryDto, Category.class)).thenReturn(savedCategory);
        when(modelMapper.map(savedCategory, CategoryDto.class)).thenReturn(categoryDto);

        // When
        CategoryDto result = underTest.addNewCategory(newCategoryDto);

        // Then
        assertEquals(categoryDto, result);
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    void addNewCategory_shouldThrowDuplicateCategoryException_whenCategoryExists() {
        // Given
        NewCategoryDto newCategoryDto = new NewCategoryDto();
        newCategoryDto.setName("Existing Category");

        when(categoryRepository.existsByName(newCategoryDto.getName())).thenReturn(true);

        // When, Then
        assertThrows(DuplicateCategoryException.class, () -> underTest.addNewCategory(newCategoryDto));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void deleteCategoryById_categoryExists_categoryDeleted() {
        // Given
        Long categoryId = 1L;
        Category category = Category.builder().id(categoryId).name("name").build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        underTest.deleteCategoryById(categoryId);

        // Then
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void deleteCategoryById_categoryNotFound_shouldThrowCategoryNotFoundException() {
        // Given
        Long categoryId = 1L;

        // When
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Then
        assertThrows(CategoryNotFoundException.class, () -> underTest.deleteCategoryById(categoryId));
        verify(categoryRepository, never()).deleteById(anyLong());
    }

    @Test
    void modifyCategory_shouldModifyExistingCategory() throws DuplicateCategoryException, CategoryNotFoundException {
        // Given
        Long categoryId = 1L;
        String modifiedName = "Modified Category";

        NewCategoryDto modifiedCategoryDto = new NewCategoryDto();
        modifiedCategoryDto.setName(modifiedName);

        Category oldCategory = Category.builder().id(categoryId).name("Old Category").build();
        Category modifiedCategory = Category.builder().id(categoryId).name(modifiedName).build();

        when(categoryRepository.existsByName(modifiedName)).thenReturn(false);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(oldCategory));
        when(categoryRepository.save(oldCategory)).thenReturn(modifiedCategory);

        // Setup modelMapper to return a CategoryDto with the modified name
        CategoryDto modifiedCategoryDtoResult = new CategoryDto();
        modifiedCategoryDtoResult.setName(modifiedName);
        when(modelMapper.map(modifiedCategory, CategoryDto.class)).thenReturn(modifiedCategoryDtoResult);

        // When
        CategoryDto result = underTest.modifyCategory(categoryId, modifiedCategoryDto);

        // Then
        assertEquals(modifiedName, result.getName());
        verify(categoryRepository, times(1)).save(oldCategory);
        verify(modelMapper, times(1)).map(modifiedCategory, CategoryDto.class);
    }

    @Test
    void modifyCategory_shouldThrowDuplicateCategoryException_whenModifiedNameExists() {
        // Given
        Long categoryId = 1L;
        String modifiedName = "Duplicate Category";

        NewCategoryDto modifiedCategoryDto = new NewCategoryDto();
        modifiedCategoryDto.setName(modifiedName);

        when(categoryRepository.existsByName(modifiedName)).thenReturn(true);

        // When, Then
        assertThrows(DuplicateCategoryException.class, () -> underTest.modifyCategory(categoryId, modifiedCategoryDto));
        verify(categoryRepository, never()).findById(anyLong());
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void modifyCategory_shouldThrowCategoryNotFoundException_whenCategoryNotFound() {
        // Given
        Long categoryId = 1L;
        String modifiedName = "Modified Category";

        NewCategoryDto modifiedCategoryDto = new NewCategoryDto();
        modifiedCategoryDto.setName(modifiedName);

        when(categoryRepository.existsByName(modifiedName)).thenReturn(false);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(CategoryNotFoundException.class, () -> underTest.modifyCategory(categoryId, modifiedCategoryDto));
        verify(categoryRepository, never()).save(any());
    }

}