package dev.akorovai.AdvancedToDoAPI.category;


import dev.akorovai.AdvancedToDoAPI.category.categoryExceptions.CategoryNotFoundException;
import dev.akorovai.AdvancedToDoAPI.category.categoryExceptions.DuplicateCategoryException;
import dev.akorovai.AdvancedToDoAPI.category.categoryExceptions.EmptyCategoryListException;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories() throws EmptyCategoryListException;

    CategoryDto addNewCategory(NewCategoryDto newCategoryDto) throws DuplicateCategoryException;

    CategoryDto modifyCategory(Long IdCategory, NewCategoryDto newCategoryDto) throws DuplicateCategoryException, CategoryNotFoundException;

    void deleteCategoryById(Long idCategory);
}
