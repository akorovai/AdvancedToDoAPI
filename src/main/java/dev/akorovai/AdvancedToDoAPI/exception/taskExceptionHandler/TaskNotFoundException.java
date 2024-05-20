package dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(Long IdTask) {
        super("Task with ID " + IdTask + " does not exist");
        log.warn("Task with ID " + IdTask + " does not exist");
    }
}
