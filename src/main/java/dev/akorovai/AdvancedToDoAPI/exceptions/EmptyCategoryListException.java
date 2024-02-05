package dev.akorovai.AdvancedToDoAPI.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "There is no categories")
public class EmptyCategoryListException extends Exception {

    public EmptyCategoryListException() {
        log.warn("No categories found.");
    }
}