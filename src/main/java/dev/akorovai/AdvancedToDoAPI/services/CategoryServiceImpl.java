package dev.akorovai.AdvancedToDoAPI.services;

import dev.akorovai.AdvancedToDoAPI.dto.CategoryDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewCategoryDto;
import dev.akorovai.AdvancedToDoAPI.entities.Category;
import dev.akorovai.AdvancedToDoAPI.exceptions.CategoryNotFoundException;
import dev.akorovai.AdvancedToDoAPI.exceptions.DuplicateCategoryException;
import dev.akorovai.AdvancedToDoAPI.exceptions.EmptyCategoryListException;
import dev.akorovai.AdvancedToDoAPI.repositories.CategoryRepository;
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
        log.info("Retrieved all labels successfully.");
        return Optional.of(categoryDtoList).filter(list -> !list.isEmpty()).orElseThrow(EmptyCategoryListException::new);
    }

    @Override
    public CategoryDto addNewCategory(NewCategoryDto newCategoryDto) throws DuplicateCategoryException {
        if (categoryRepository.existsByName(newCategoryDto.getName())) {
            throw new DuplicateCategoryException("Label with name " + newCategoryDto.getName() + " already exists");
        }
        Category savedCategory = categoryRepository.save(modelMapper.map(newCategoryDto, Category.class));
        log.info("New label added successfully: {}", savedCategory.getName());
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto modifyCategory(Long IdCategory, NewCategoryDto modifiedCategoryDto) throws DuplicateCategoryException, CategoryNotFoundException {
        if (categoryRepository.existsByName(modifiedCategoryDto.getName())) {
            throw new DuplicateCategoryException("Label with name " + modifiedCategoryDto.getName() + " already exists");
        }

        Optional<Category> oldCategoryOptional = categoryRepository.findById(IdCategory);

        if (oldCategoryOptional.isEmpty()) {
            throw new CategoryNotFoundException("Label with ID " + IdCategory + " not found");
        }

        Category oldCategory = oldCategoryOptional.get();
        oldCategory.setName(modifiedCategoryDto.getName());

        Category modifiedCategory = categoryRepository.save(oldCategory);
        log.info("Modified label successfully. ID: {}, New Name: {}", IdCategory, modifiedCategoryDto.getName());

        return modelMapper.map(modifiedCategory, CategoryDto.class);
    }


}
