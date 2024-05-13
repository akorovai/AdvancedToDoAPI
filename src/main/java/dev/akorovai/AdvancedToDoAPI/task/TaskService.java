package dev.akorovai.AdvancedToDoAPI.task;

import dev.akorovai.AdvancedToDoAPI.task.taskExceptionHandler.TaskNotFoundException;

public interface TaskService {
    TaskDto getTaskById(Long idTask) throws TaskNotFoundException;

    TaskDto addNewTask(NewTaskDto newTaskDto);

    void deleteTaskById(Long idTask);
}
