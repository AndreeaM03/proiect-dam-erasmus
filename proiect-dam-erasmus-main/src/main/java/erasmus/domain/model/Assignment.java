package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    private String id;

    private int billableHours;

    // Relatie n-1: mai multe alocari apartin unei Activitati
    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false) // cheie straina catre Activity
    private Activity activity;

    // Relatie n-1: mai multe alocari apartin unui User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // cheie straina catre User
    private User user;
    
}