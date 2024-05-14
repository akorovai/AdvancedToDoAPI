package dev.akorovai.AdvancedToDoAPI.task;

import dev.akorovai.AdvancedToDoAPI.category.CategoryDto;
import lombok.*;


import java.time.LocalDateTime;

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
}
