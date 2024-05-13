package dev.akorovai.AdvancedToDoAPI.category;

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
    public ResponseEntity<List<CategoryDto>> getAllCategories()  {
        List<CategoryDto> categoryList = categoryService.getAllCategories();

        return ResponseEntity.ok().body(categoryList);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addNewCategory(@RequestBody NewCategoryDto newCategoryDto)  {
        CategoryDto response = categoryService.addNewCategory(newCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{IdCategory}")
    public ResponseEntity<CategoryDto> modifyCategoryById(@PathVariable Long IdCategory, @RequestBody NewCategoryDto modifiedCategoryDto) {
        CategoryDto response = categoryService.modifyCategory(IdCategory, modifiedCategoryDto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{IdCategory}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long IdCategory) {
        categoryService.deleteCategoryById(IdCategory);
        return ResponseEntity.ok().build();
    }

}
