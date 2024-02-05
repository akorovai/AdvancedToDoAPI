package dev.akorovai.AdvancedToDoAPI.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "There is no labels")
public class EmptyLabelListException extends Exception {
    public EmptyLabelListException() {
        log.warn("No labels found.");
    }
}
