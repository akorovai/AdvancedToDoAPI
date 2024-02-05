package dev.akorovai.AdvancedToDoAPI.controllers;

import dev.akorovai.AdvancedToDoAPI.dto.CategoryDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewCategoryDto;
import dev.akorovai.AdvancedToDoAPI.exceptions.CategoryNotFoundException;
import dev.akorovai.AdvancedToDoAPI.exceptions.DuplicateCategoryException;
import dev.akorovai.AdvancedToDoAPI.exceptions.EmptyCategoryListException;
import dev.akorovai.AdvancedToDoAPI.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() throws EmptyCategoryListException {
        List<CategoryDto> categoryList = categoryService.getAllCategories();

        return ResponseEntity.ok().body(categoryList);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addNewCategory(@RequestBody NewCategoryDto newCategoryDto) throws DuplicateCategoryException {
        CategoryDto response = categoryService.addNewCategory(newCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{IdCategory}")
    public ResponseEntity<CategoryDto> modifyCategory(@PathVariable Long IdCategory, @RequestBody NewCategoryDto modifiedCategoryDto) throws DuplicateCategoryException, CategoryNotFoundException {
        CategoryDto response = categoryService.modifyCategory(IdCategory, modifiedCategoryDto);
        return ResponseEntity.ok().body(response);
    }

}
