package dev.akorovai.AdvancedToDoAPI.exception.labelException;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
public class DuplicateLabelException extends RuntimeException{
    public DuplicateLabelException(String title) {
        super("Label with title " + title + " already exists");
        log.error("Label with title " + title + " already exists");
    }
}
