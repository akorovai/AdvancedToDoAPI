package dev.akorovai.AdvancedToDoAPI.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "Label")
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

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at",columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime modifiedAt;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Label label = (Label) o;
        return Objects.equals(id, label.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
