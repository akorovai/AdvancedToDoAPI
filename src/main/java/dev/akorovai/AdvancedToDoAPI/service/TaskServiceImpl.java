package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.NewTaskDto;
import dev.akorovai.AdvancedToDoAPI.dto.TaskDto;
import dev.akorovai.AdvancedToDoAPI.entity.Task;
import dev.akorovai.AdvancedToDoAPI.entity.TaskType;
import dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler.*;
import dev.akorovai.AdvancedToDoAPI.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Override
    public TaskDto getTaskById(Long IdTask) throws TaskNotFoundException {
        Optional<Task> taskOptional = taskRepository.findById(IdTask);

        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException(IdTask);
        }

        log.info("New task added successfully: {}", taskOptional.get().getTitle());

        return modelMapper.map(taskOptional.get(), TaskDto.class);
    }

    @Override
    public TaskDto addNewTask(NewTaskDto newTaskDto) {
        Arrays.stream(TaskType.values())
                .filter(taskType -> taskType == newTaskDto.getTaskType())
                .findAny()
                .orElseThrow(() -> new TaskTypeNotFoundException(newTaskDto.getTaskType()));


        if (!Character.isLetter(newTaskDto.getPriority())) {
            throw new IncorrectPriorityException(newTaskDto.getPriority());
        }
        if (newTaskDto.getTaskType() == TaskType.SIMPLE && !newTaskDto.getSubtasks().isEmpty()) {
            throw new SimpleTaskWithSubtasksException();
        }

        if (newTaskDto.getTaskType() != TaskType.SIMPLE && newTaskDto.getSubtasks().size() < 2) {
            throw new NotSimpleTaskWithInsufficientSubtasksException(newTaskDto.getSubtasks().size());
        }

        TaskDto savedTask = null;

        log.info("New task added successfully: {}", savedTask.getId());

        return modelMapper.map(savedTask, TaskDto.class);
    }


}
