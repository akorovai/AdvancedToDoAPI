package dev.akorovai.AdvancedToDoAPI.task;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/sorted")
    public ResponseEntity<List<TaskDto>> getAllTasksSorted(@RequestParam(defaultValue = "status") String sortBy) {
        List<TaskDto> taskDtos = taskService.getAllTasksSorted(sortBy);
        return ResponseEntity.ok(taskDtos);
    }
    @PostMapping
    @Transactional
    public ResponseEntity<TaskDto> addNewTask(@RequestBody NewTaskDto newTaskDto){
        TaskDto taskDto = taskService.addNewTask(newTaskDto);
        return ResponseEntity.ok(taskDto);
    }

    @DeleteMapping("/{IdTask}")
    @Transactional
    public ResponseEntity<?> deleteTask(@PathVariable Long IdTask) {
        taskService.deleteTaskById(IdTask);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{IdTask}")
    @Transactional
    public ResponseEntity<?> modifyTask(@RequestBody ModifiedTaskDto mtd, @PathVariable Long IdTask) {
        TaskDto response = taskService.modifyTask(IdTask, mtd);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{IdTask}")
    @Transactional
    public ResponseEntity<TaskDto> changeStatusToNextStep(@PathVariable Long IdTask) {
        TaskDto response = taskService.moveToNextStep(IdTask);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{IdTask}")
    @Transactional
    public ResponseEntity<TaskDto> changeStatusToPreviousStep(@PathVariable Long IdTask) {
        TaskDto response = taskService.moveToPreviousStep(IdTask);
        return ResponseEntity.ok(response);
    }
}
