package dev.akorovai.AdvancedToDoAPI.dto;

import dev.akorovai.AdvancedToDoAPI.entity.Status;
import dev.akorovai.AdvancedToDoAPI.entity.TaskType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private Character priority;

    private CategoryDto category;

    private LocalDateTime dueDate;

    private Status status;

    private TaskType taskType;
    private List<SubtaskDto> subtasks; // Add this line
}