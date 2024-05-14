package dev.akorovai.AdvancedToDoAPI.taskhistory;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks-history")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskHistoryController {
    private TaskHistoryService taskHistoryService;
}
