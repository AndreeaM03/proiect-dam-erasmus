package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "slots")
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int availableSlots;

    // Relatie n-1: mai multe locuri apartin unei Universitati
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false) // cheie straina catre University
    private University university;

}