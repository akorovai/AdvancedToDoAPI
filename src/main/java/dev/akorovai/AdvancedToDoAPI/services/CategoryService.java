package dev.akorovai.AdvancedToDoAPI.services;


import dev.akorovai.AdvancedToDoAPI.dto.CategoryDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewCategoryDto;
import dev.akorovai.AdvancedToDoAPI.entities.Category;
import dev.akorovai.AdvancedToDoAPI.exceptions.CategoryNotFoundException;
import dev.akorovai.AdvancedToDoAPI.exceptions.DuplicateCategoryException;
import dev.akorovai.AdvancedToDoAPI.exceptions.EmptyCategoryListException;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories() throws EmptyCategoryListException;

    CategoryDto addNewCategory(NewCategoryDto newCategoryDto) throws DuplicateCategoryException;

    CategoryDto modifyCategory(Long IdCategory, NewCategoryDto newCategoryDto) throws DuplicateCategoryException, CategoryNotFoundException;
}
