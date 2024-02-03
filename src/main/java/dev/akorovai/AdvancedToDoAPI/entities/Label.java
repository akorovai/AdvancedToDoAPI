package dev.akorovai.AdvancedToDoAPI.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Label")
@Data
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id")
    private Long id;

    @Column(name = "title", length = 30)
    private String title;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
