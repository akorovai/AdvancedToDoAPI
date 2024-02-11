package dev.akorovai.AdvancedToDoAPI.exception.labelException;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
public class EmptyLabelListException extends RuntimeException {
    public EmptyLabelListException() {
        super("No labels found.");
        log.warn("No labels found.");
    }
}
