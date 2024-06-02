package dev.akorovai.AdvancedToDoAPI.controller;

import dev.akorovai.AdvancedToDoAPI.dto.TaskHistoryDto;
import dev.akorovai.AdvancedToDoAPI.service.TaskHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks-history")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

    @GetMapping
    public ResponseEntity<?> getTaskHistories(
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(defaultValue = "id") String sortBy) {
        log.debug("Received request to fetch task histories with taskId: {}, from: {}, to: {}, sortBy: {}", taskId, from, to, sortBy);

        List<TaskHistoryDto> taskHistories = taskHistoryService.getTaskHistories(taskId, from, to, sortBy);
        log.debug("Returning {} task histories", taskHistories.size());

        return ResponseEntity.ok().body(taskHistories);
    }
}
