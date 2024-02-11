package dev.akorovai.AdvancedToDoAPI.controller;

import dev.akorovai.AdvancedToDoAPI.dto.NewTaskDto;
import dev.akorovai.AdvancedToDoAPI.dto.TaskDto;
import dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler.TaskNotFoundException;
import dev.akorovai.AdvancedToDoAPI.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{IdTask}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long IdTask) {
        TaskDto taskDto = taskService.getTaskById(IdTask);
        return ResponseEntity.ok(taskDto);
    }

    @PostMapping
    public ResponseEntity<TaskDto> addNewTask(@RequestBody NewTaskDto newTaskDto){
        TaskDto taskDto = taskService.addNewTask(newTaskDto);
        return ResponseEntity.ok(taskDto);
    }
}
