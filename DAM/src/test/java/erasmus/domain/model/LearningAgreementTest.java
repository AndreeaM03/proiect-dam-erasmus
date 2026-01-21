package erasmus.domain.model;

import erasmus.commons.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "learning_agreements")
public class LearningAgreementTest {

    @Id
    private String id;

    private Status status;

    // Relatie 1-1: LA-ul apartine unei singure mobilitati
    @OneToOne
    @JoinColumn(name = "mobility_id", nullable = false) // cheie straina catre Mobility
    private Mobility mobility;
    
}