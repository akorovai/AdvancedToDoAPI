package dev.akorovai.AdvancedToDoAPI.task.taskExceptionHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class NotSimpleTaskWithInsufficientSubtasksException extends RuntimeException {
    public NotSimpleTaskWithInsufficientSubtasksException(int subtaskCount) {
        super("A task other than SIMPLE must have at least two subtasks, but found only " + subtaskCount + ".");
        log.error("A task other than SIMPLE must have at least two subtasks, but found only " + subtaskCount + ".");
    }
}