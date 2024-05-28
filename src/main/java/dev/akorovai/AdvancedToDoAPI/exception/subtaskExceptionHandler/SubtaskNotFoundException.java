package dev.akorovai.AdvancedToDoAPI.exception.subtaskExceptionHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SubtaskNotFoundException extends RuntimeException {
    public SubtaskNotFoundException(Long subtaskId) {
        super("Subtask with ID " + subtaskId + " does not exist");
        log.warn("Subtask with ID " + subtaskId + " does not exist");
    }
}
