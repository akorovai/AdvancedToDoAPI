package dev.akorovai.AdvancedToDoAPI.category.categoryExceptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EmptyCategoryListException extends RuntimeException {
    public EmptyCategoryListException() {
        super("No categories found.");
        log.warn("No categories found.");
    }
}