package dev.akorovai.AdvancedToDoAPI.exception.labelException;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
public class LabelNotFoundException extends RuntimeException{
    public LabelNotFoundException(Long IdLabel) {
        super("Label not found with ID: " + IdLabel);
        log.warn("Label not found with ID: {}", IdLabel);
    }
}
