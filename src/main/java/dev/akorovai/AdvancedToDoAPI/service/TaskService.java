package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.ModifiedTaskDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewTaskDto;
import dev.akorovai.AdvancedToDoAPI.dto.TaskDto;
import dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler.TaskNotFoundException;

import java.util.List;

public interface TaskService {
    TaskDto getTaskById(Long idTask) throws TaskNotFoundException;

    TaskDto addNewTask(NewTaskDto newTaskDto);

    void deleteTaskById(Long idTask);

    TaskDto modifyTask(Long idTask, ModifiedTaskDto mtd);

    TaskDto moveToNextStep(Long idTask);

    TaskDto moveToPreviousStep(Long idTask);

    List<TaskDto> getAllTasksSorted(String sortBy);
}
