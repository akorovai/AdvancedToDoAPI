package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.CategoryDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewCategoryDto;
import dev.akorovai.AdvancedToDoAPI.entity.Category;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.CategoryNotFoundException;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.DuplicateCategoryException;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.EmptyCategoryListException;
import dev.akorovai.AdvancedToDoAPI.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getAllCategories() throws EmptyCategoryListException {
        List<CategoryDto> categoryDtoList = categoryRepository.findAll().stream().map((category) -> modelMapper.map(category, CategoryDto.class)).toList();
        log.info("Retrieved all categories successfully.");
        return Optional.of(categoryDtoList).filter(list -> !list.isEmpty()).orElseThrow(EmptyCategoryListException::new);
    }

    @Override
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto) throws DuplicateCategoryException {
        if (categoryRepository.existsByName(newCategoryDto.getName())) {
            throw new DuplicateCategoryException("Category with name " + newCategoryDto.getName() + " already exists");
        }
        Category savedCategory = categoryRepository.save(modelMapper.map(newCategoryDto, Category.class));
        log.info("New category added successfully: {}", savedCategory.getName());
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto modifyCategory(Long IdCategory, NewCategoryDto modifiedCategoryDto) throws DuplicateCategoryException, CategoryNotFoundException {
        if (categoryRepository.existsByName(modifiedCategoryDto.getName())) {
            throw new DuplicateCategoryException(modifiedCategoryDto.getName());
        }

        Optional<Category> oldCategoryOptional = categoryRepository.findById(IdCategory);

        if (oldCategoryOptional.isEmpty()) {
            throw new CategoryNotFoundException(IdCategory);
        }

        Category oldCategory = oldCategoryOptional.get();
        oldCategory.setName(modifiedCategoryDto.getName());

        Category modifiedCategory = categoryRepository.save(oldCategory);
        log.info("Modified category successfully. ID: {}, New Name: {}", IdCategory, modifiedCategoryDto.getName());

        return modelMapper.map(modifiedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategoryById(Long IdCategory) {
        Optional<Category> categoryToDelete = categoryRepository.findById(IdCategory);

        if (categoryToDelete.isEmpty()) {
            throw new CategoryNotFoundException(IdCategory);
        }

        categoryRepository.deleteById(IdCategory);

        log.info("Category deleted successfully with ID: {}", IdCategory);
    }


}
