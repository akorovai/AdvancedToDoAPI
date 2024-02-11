package dev.akorovai.AdvancedToDoAPI.dto;

import dev.akorovai.AdvancedToDoAPI.entity.Status;
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
