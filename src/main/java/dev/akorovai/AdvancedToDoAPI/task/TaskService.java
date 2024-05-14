package dev.akorovai.AdvancedToDoAPI.task;

import dev.akorovai.AdvancedToDoAPI.task.taskExceptionHandler.TaskNotFoundException;

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
