package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.NewTaskDto;
import dev.akorovai.AdvancedToDoAPI.dto.TaskDto;
import dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler.TaskNotFoundException;

public interface TaskService {
    TaskDto getTaskById(Long idTask) throws TaskNotFoundException;

    TaskDto addNewTask(NewTaskDto newTaskDto);
}
