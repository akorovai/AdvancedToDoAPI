package dev.akorovai.AdvancedToDoAPI.task;

import dev.akorovai.AdvancedToDoAPI.category.Category;
import dev.akorovai.AdvancedToDoAPI.entity.Status;
import dev.akorovai.AdvancedToDoAPI.entity.TaskType;
import dev.akorovai.AdvancedToDoAPI.category.categoryExceptions.CategoryNotFoundException;

import dev.akorovai.AdvancedToDoAPI.category.CategoryRepository;
import dev.akorovai.AdvancedToDoAPI.task.taskExceptionHandler.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;


    @Override
    public TaskDto getTaskById(Long IdTask) throws TaskNotFoundException {
        Task task = taskRepository.findById(IdTask).orElseThrow(() -> new TaskNotFoundException(IdTask));

        log.info("Retrieved task successfully: {}", task.getTitle());

        return modelMapper.map(task, TaskDto.class);
    }


    @Override
    public TaskDto addNewTask(NewTaskDto newTaskDto) {
        // check task type
        Arrays.stream(TaskType.values()).filter(taskType -> taskType == newTaskDto.getTaskType()).findAny().orElseThrow(() -> new TaskTypeNotFoundException(newTaskDto.getTaskType()));

        // priority check
        if (!Character.isLetter(newTaskDto.getPriority())) {
            throw new IncorrectPriorityException(newTaskDto.getPriority());
        }

        // no subtasks for simple tasks
        if (newTaskDto.getTaskType() == TaskType.SIMPLE && !newTaskDto.getSubtasks().isEmpty()) {
            throw new SimpleTaskWithSubtasksException();
        }


        if (newTaskDto.getTaskType() != TaskType.SIMPLE && (newTaskDto.getSubtasks() == null || newTaskDto.getSubtasks().size() < 2)) {
            throw new NotSimpleTaskWithInsufficientSubtasksException(newTaskDto.getSubtasks() != null ? newTaskDto.getSubtasks().size() : 0);
        }


        Task newTask = modelMapper.map(newTaskDto, Task.class);
        newTask.setStatus(Status.CREATED);

        Category category = categoryRepository.findById(newTaskDto.getIdCategory()).orElseThrow(() -> new CategoryNotFoundException(newTaskDto.getIdCategory()));
        newTask.setCategory(category);


        Task savedTask = taskRepository.save(newTask);

        log.info("New task added successfully: {}", savedTask.getTitle());

        return modelMapper.map(savedTask, TaskDto.class);
    }

    @Override
    public void deleteTaskById(Long idTask) {
        Optional<Task> task = taskRepository.findById(idTask);

        if(task.isEmpty()) {
            throw new TaskNotFoundException(idTask);
        }

        taskRepository.delete(task.get());
    }
}
