package dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SimpleTaskWithSubtasksException extends RuntimeException {
    public SimpleTaskWithSubtasksException() {
        super("A SIMPLE task cannot have subtasks.");
        log.error("A SIMPLE task cannot have subtasks.");
    }
}