package dev.akorovai.AdvancedToDoAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.akorovai.AdvancedToDoAPI.entity.Status;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TaskHistoryDto {
    private Long id;

    private Status newStatus;

    private LocalDateTime timestamp;

    private Long IdTask;

}
