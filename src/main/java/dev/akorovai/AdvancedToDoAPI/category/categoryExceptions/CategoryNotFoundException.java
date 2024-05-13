package dev.akorovai.AdvancedToDoAPI.category.categoryExceptions;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long idCategory) {
        super("Category not found with ID: " + idCategory);
        log.warn("Category not found with ID: {}", idCategory);
    }
}