package dev.akorovai.AdvancedToDoAPI.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LabelDto {
    private Long id;
    private String title;
}
