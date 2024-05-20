package dev.akorovai.AdvancedToDoAPI.controller;

import dev.akorovai.AdvancedToDoAPI.dto.TaskHistoryDto;
import dev.akorovai.AdvancedToDoAPI.service.TaskHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks-history")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskHistoryController {
    private TaskHistoryService taskHistoryService;

    @GetMapping
    public List<TaskHistoryDto> getTaskHistories(
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(defaultValue = "id") String sortBy) {
        return taskHistoryService.getTaskHistories(taskId, from, to, sortBy);
    }
}
