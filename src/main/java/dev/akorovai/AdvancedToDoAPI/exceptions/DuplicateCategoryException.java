package dev.akorovai.AdvancedToDoAPI.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Category with such name already exists")
public class DuplicateCategoryException extends Exception{

    public DuplicateCategoryException(String message) {
        super(message);
        log.error("Error while adding a new category: {}",message);
    }
}

