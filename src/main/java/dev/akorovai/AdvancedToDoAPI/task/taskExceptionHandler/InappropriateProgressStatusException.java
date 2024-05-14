package dev.akorovai.AdvancedToDoAPI.task.taskExceptionHandler;

import dev.akorovai.AdvancedToDoAPI.entity.Status;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class InappropriateProgressStatusException extends RuntimeException{
    public InappropriateProgressStatusException(Status status) {
        super("Status can't be " + status.toString() + " to move progress in such direction");
        log.error("Status can't be {} to move progress in such direction", status);
    }
}
