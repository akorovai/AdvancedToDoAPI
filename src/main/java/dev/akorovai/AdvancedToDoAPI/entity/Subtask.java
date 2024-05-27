package dev.akorovai.AdvancedToDoAPI.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Subtask")
@EqualsAndHashCode

public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtask_id")
    private Long id;
    @Column(name = "title", length = 128)
    private String title;
    @Column(name = "status", length = 11)
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime modifiedAt;
    @Column(name = "order_index")
    private int orderIndex;

    public Subtask(String title, Status status, int orderIndex) {
        this.title = title;
        this.status = status;
        this.orderIndex = orderIndex;
    }

    @Override
    public String toString() {
        return "Subtask{" + "id=" + id + ", title='" + title + '\'' + ", status=" + status + ", orderIndex=" + orderIndex + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, status, orderIndex);
    }


}
