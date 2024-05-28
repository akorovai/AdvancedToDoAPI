package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.ModifiedTaskDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewTaskDto;
import dev.akorovai.AdvancedToDoAPI.dto.SubtaskDto;
import dev.akorovai.AdvancedToDoAPI.dto.TaskDto;
import dev.akorovai.AdvancedToDoAPI.entity.*;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.CategoryNotFoundException;
import dev.akorovai.AdvancedToDoAPI.exception.subtaskExceptionHandler.SubtaskNotFoundException;
import dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler.*;
import dev.akorovai.AdvancedToDoAPI.repository.CategoryRepository;
import dev.akorovai.AdvancedToDoAPI.repository.SubtaskRepository;
import dev.akorovai.AdvancedToDoAPI.repository.TaskHistoryRepository;
import dev.akorovai.AdvancedToDoAPI.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final TaskHistoryRepository taskHistoryRepository;

    @Override
    public TaskDto getTaskById(Long IdTask) throws TaskNotFoundException {
        Task task = taskRepository.findById(IdTask).orElseThrow(() -> new TaskNotFoundException(IdTask));
        log.info("Retrieved task successfully: {}", task.getTitle());

        List<SubtaskDto> subtaskDtos = task.getSubtasks().stream().map(subtask -> modelMapper.map(subtask, SubtaskDto.class)).collect(Collectors.toList());
        TaskDto taskDto = modelMapper.map(task, TaskDto.class);
        taskDto.setSubtasks(subtaskDtos);

        return taskDto;
    }

    @Override
    public TaskDto addNewTask(NewTaskDto newTaskDto) {
        validateTaskType(newTaskDto);
        validatePriority(newTaskDto.getPriority());
        validateSubtasks(newTaskDto);

        Category category = categoryRepository.findById(newTaskDto.getIdCategory()).orElseThrow(() -> new CategoryNotFoundException(newTaskDto.getIdCategory()));

        Task newTask = new Task();
        newTask.setTitle(newTaskDto.getTitle());
        newTask.setCategory(category);
        newTask.setPriority(newTaskDto.getPriority());
        newTask.setDescription(newTaskDto.getDescription());
        newTask.setDueDate(newTaskDto.getDueDate());
        newTask.setTaskType(newTaskDto.getTaskType());
        newTask.setSubtasks(new HashSet<>());
        newTask.setStatus(Status.CREATED);

        Task savedTask = taskRepository.save(newTask);
        Set<SubtaskDto> subtaskDtos = newTaskDto.getSubtasks();
        if (Objects.nonNull(subtaskDtos)) {
            int orderIndex = 1;
            Set<Subtask> subtasks = new HashSet<>();
            for (SubtaskDto dto : subtaskDtos) {
                Subtask subtask = new Subtask();
                subtask.setTitle(dto.getTitle());
                subtask.setStatus(dto.getStatus());
                subtask.setOrderIndex(orderIndex++);
                subtask.setTask(savedTask);
                subtasks.add(subtask);
            }
            subtaskRepository.saveAllAndFlush(subtasks);
        }
        saveTaskHistory(savedTask, "CREATE", savedTask.getStatus());

        log.info("New task added successfully: {}", savedTask.getTitle());
        return modelMapper.map(savedTask, TaskDto.class);
    }

    @Override
    public void deleteTaskById(Long taskId) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        taskRepository.delete(task);
        log.info("Task with id {} deleted successfully", taskId);
    }

    @Override
    public TaskDto modifyTask(Long idTask, ModifiedTaskDto mtd) {
        Task task = taskRepository.findById(idTask).orElseThrow(() -> new TaskNotFoundException(idTask));

        String newTitle = mtd.getTitle();
        if (!isNotEmptyField(newTitle)) {
            task.setTitle(newTitle);
        }

        String newDescription = mtd.getDescription();
        if (!isNotEmptyField(newDescription)) {
            task.setDescription(newDescription);
        }

        Character newPriority = mtd.getPriority();
        if (newPriority != null) {
            validatePriority(newPriority);
            task.setPriority(newPriority);
        }

        LocalDateTime newDueDate = mtd.getDueDate();
        if (newDueDate != null && newDueDate.isAfter(LocalDateTime.now())) {
            task.setDueDate(newDueDate);
        }

        Long categoryId = mtd.getIdCategory();
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
            task.setCategory(category);
        }

        Task updatedTask = taskRepository.save(task);
        saveTaskHistory(updatedTask, "UPDATE", updatedTask.getStatus());

        log.info("Task modified successfully: {}", updatedTask.getId());
        return modelMapper.map(updatedTask, TaskDto.class);
    }

    @Override
    public TaskDto moveToNextStep(Long taskId, Long subtaskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        Status newStatus = getNextStatus(task.getStatus(), task.getTaskType(), task, subtaskId);
        task.setStatus(newStatus);

        Task updatedTask = taskRepository.save(task);
        saveTaskHistory(updatedTask, "NEXT_STEP", task.getStatus());

        log.info("Task {} moved to next step. New status: {}", taskId, task.getStatus());
        TaskDto taskDto = modelMapper.map(updatedTask, TaskDto.class);
        log.info("Mapped Task to TaskDto: {}", taskDto);
        return taskDto;
    }

    @Override
    public TaskDto moveToPreviousStep(Long taskId, Long subtaskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        Status newStatus = getPreviousStatus(task.getStatus(), task.getTaskType(), task, subtaskId);
        task.setStatus(newStatus);

        Task updatedTask = taskRepository.save(task);
        saveTaskHistory(updatedTask, "PREVIOUS_STEP", task.getStatus());

        log.info("Task {} moved to previous step. New status: {}", taskId, task.getStatus());
        TaskDto taskDto = modelMapper.map(updatedTask, TaskDto.class);
        log.info("Mapped Task to TaskDto: {}", taskDto);
        return taskDto;
    }

    private Status getNextStatus(Status currentStatus, TaskType taskType, Task task, Long subtaskId) {
        switch (taskType) {
            case SIMPLE:
                return switch (currentStatus) {
                    case CREATED -> Status.IN_PROGRESS;
                    case IN_PROGRESS -> Status.DONE;
                    default -> throw new InappropriateProgressStatusException(currentStatus);
                };
            case ORDERED:
                List<Subtask> orderedSubtasks = task.getSubtasks().stream()
                        .sorted(Comparator.comparingInt(Subtask::getOrderIndex))
                        .toList();
                for (Subtask subtask : orderedSubtasks) {
                    if (subtask.getStatus() != Status.DONE) {
                        subtask.setStatus(Status.DONE);
                        subtaskRepository.save(subtask);
                        return Status.IN_PROGRESS;
                    }
                }
                return Status.DONE;
            case SUMMARY:


                Subtask summarySubtask = subtaskRepository.findById(subtaskId)
                        .orElseThrow(() -> new SubtaskNotFoundException(subtaskId));
                if (summarySubtask.getStatus() == Status.DONE) {
                    throw new InappropriateProgressStatusException(summarySubtask.getStatus());
                }
                summarySubtask.setStatus(Status.DONE);
                subtaskRepository.save(summarySubtask);

                boolean allSubtasksDone = task.getSubtasks().stream().allMatch(st -> st.getStatus() == Status.DONE);

                return allSubtasksDone ? Status.DONE : Status.IN_PROGRESS;
            default:
                throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }

    private Status getPreviousStatus(Status currentStatus, TaskType taskType, Task task, Long subtaskId) {
        switch (taskType) {
            case SIMPLE:
                return switch (currentStatus) {
                    case DONE -> Status.IN_PROGRESS;
                    case IN_PROGRESS -> Status.CREATED;
                    default -> throw new InappropriateProgressStatusException(currentStatus);
                };
            case ORDERED:
                List<Subtask> orderedSubtasks = task.getSubtasks().stream()
                        .sorted(Comparator.comparingInt(Subtask::getOrderIndex).reversed())
                        .toList();
                for (Subtask subtask : orderedSubtasks) {
                    if (subtask.getStatus() == Status.DONE) {
                        subtask.setStatus(Status.IN_PROGRESS);
                        subtaskRepository.save(subtask);
                        return Status.IN_PROGRESS;
                    }
                }
                return Status.CREATED;
            case SUMMARY:


                Subtask summarySubtask = subtaskRepository.findById(subtaskId)
                        .orElseThrow(() -> new SubtaskNotFoundException(subtaskId));
                if (summarySubtask.getStatus() == Status.CREATED) {
                    throw new InappropriateProgressStatusException(summarySubtask.getStatus());
                }
                summarySubtask.setStatus(Status.DONE);
                subtaskRepository.save(summarySubtask);

                boolean anySubtasksNotDone = task.getSubtasks().stream().anyMatch(st -> st.getStatus() != Status.DONE);

                return anySubtasksNotDone ? Status.IN_PROGRESS : Status.CREATED;
            default:
                throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }



    @Override
    public List<TaskDto> getAllTasksSorted(String sortBy) {
        List<Task> tasks = taskRepository.findAll();

        if (sortBy.equals("status")) {
            tasks.sort(Comparator.comparing(task -> task.getStatus().ordinal()));
        }
        if (sortBy.equals("dueDate")) {
            tasks.sort(Comparator.comparing(Task::getDueDate));
        }

        log.debug("Sorted tasks: {}", tasks);

        return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
    }

    private boolean isNotEmptyField(String field) {
        return field == null || field.trim().isEmpty();
    }

    private void validateTaskType(NewTaskDto newTaskDto) {
        Arrays.stream(TaskType.values()).filter(taskType -> taskType == newTaskDto.getTaskType()).findAny().orElseThrow(() -> new TaskTypeNotFoundException(newTaskDto.getTaskType()));
    }

    private void validatePriority(Character priority) {
        if (!Character.isLetter(priority)) {
            throw new IncorrectPriorityException(priority);
        }
    }

    private void validateSubtasks(NewTaskDto newTaskDto) {
        if (newTaskDto.getTaskType() == TaskType.SIMPLE && !newTaskDto.getSubtasks().isEmpty()) {
            throw new SimpleTaskWithSubtasksException();
        }

        if (newTaskDto.getTaskType() != TaskType.SIMPLE && (newTaskDto.getSubtasks() == null || newTaskDto.getSubtasks().size() < 2)) {
            throw new NotSimpleTaskWithInsufficientSubtasksException(newTaskDto.getSubtasks() != null ? newTaskDto.getSubtasks().size() : 0);
        }
    }

    private void saveTaskHistory(Task task, String action, Status newStatus) {
        TaskHistory history = new TaskHistory();
        history.setTask(task);
        history.setAction(action);
        history.setNewStatus(newStatus);
        history.setTimestamp(LocalDateTime.now());
        taskHistoryRepository.save(history);
    }
}
