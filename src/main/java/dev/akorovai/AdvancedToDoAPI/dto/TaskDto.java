package dev.akorovai.AdvancedToDoAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.akorovai.AdvancedToDoAPI.entity.Status;
import dev.akorovai.AdvancedToDoAPI.entity.TaskType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private Character priority;

    private CategoryDto category;

    private LocalDateTime dueDate;

    private Status status;

    private TaskType taskType;
    private List<SubtaskDto> subtasks;
}