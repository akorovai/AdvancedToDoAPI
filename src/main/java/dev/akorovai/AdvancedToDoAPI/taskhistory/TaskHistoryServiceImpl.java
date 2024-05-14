package dev.akorovai.AdvancedToDoAPI.taskhistory;

import dev.akorovai.AdvancedToDoAPI.category.CategoryRepository;
import dev.akorovai.AdvancedToDoAPI.task.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskHistoryServiceImpl implements TaskHistoryService{

    private final ModelMapper modelMapper;
    private final TaskHistoryRepository taskHistoryRepository;

}
