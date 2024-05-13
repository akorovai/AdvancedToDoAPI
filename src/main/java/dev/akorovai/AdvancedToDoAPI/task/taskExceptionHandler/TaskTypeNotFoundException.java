package dev.akorovai.AdvancedToDoAPI.task.taskExceptionHandler;

import dev.akorovai.AdvancedToDoAPI.entity.TaskType;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class TaskTypeNotFoundException extends RuntimeException {
    public TaskTypeNotFoundException(TaskType taskType) {
        super("TaskType " + taskType + "wasn't found");
        log.error("TaskType " + taskType + "wasn't found");
    }
}
