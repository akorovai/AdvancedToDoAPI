package dev.akorovai.AdvancedToDoAPI.category.categoryExceptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DuplicateCategoryException extends RuntimeException{

    public DuplicateCategoryException(String name) {
        super("Category with name " + name + " already exists");
        log.error("Category with name " + name + " already exists");
    }
}

