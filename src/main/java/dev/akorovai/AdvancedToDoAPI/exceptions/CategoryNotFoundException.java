package dev.akorovai.AdvancedToDoAPI.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Category not found")
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
        log.error("Category not found: {}", message);
    }
}
