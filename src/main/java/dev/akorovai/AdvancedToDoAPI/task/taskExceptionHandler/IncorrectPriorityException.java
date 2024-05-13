package dev.akorovai.AdvancedToDoAPI.task.taskExceptionHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class IncorrectPriorityException extends RuntimeException{
    public IncorrectPriorityException(Character priority) {
        super("Priority should be a letter: " + priority);
        log.error("Priority should be a letter: " + priority);
    }
}
