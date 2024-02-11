package dev.akorovai.AdvancedToDoAPI.service;


import dev.akorovai.AdvancedToDoAPI.dto.CategoryDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewCategoryDto;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.CategoryNotFoundException;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.DuplicateCategoryException;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.EmptyCategoryListException;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories() throws EmptyCategoryListException;

    CategoryDto addNewCategory(NewCategoryDto newCategoryDto) throws DuplicateCategoryException;

    CategoryDto modifyCategory(Long IdCategory, NewCategoryDto newCategoryDto) throws DuplicateCategoryException, CategoryNotFoundException;

    void deleteCategoryById(Long idCategory);
}
