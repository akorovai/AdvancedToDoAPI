package dev.akorovai.AdvancedToDoAPI.subtask;

import dev.akorovai.AdvancedToDoAPI.task.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubtaskDto {

    private Long id;

    private String title;

    private Status status;
}
