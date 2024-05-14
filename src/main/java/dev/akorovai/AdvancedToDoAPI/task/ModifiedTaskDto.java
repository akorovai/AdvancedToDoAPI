package dev.akorovai.AdvancedToDoAPI.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class ModifiedTaskDto {

    private String title;

    private String description;

    private Character priority;

    private LocalDateTime dueDate;


    private Long idCategory;

}