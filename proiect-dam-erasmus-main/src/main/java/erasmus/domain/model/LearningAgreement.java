package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import erasmus.commons.enums.Status;

@Data
@Entity
@Table(name = "learning_agreements")
public class LearningAgreement {

    @Id
    private String id;

    private Status status;

    // Relatie 1-1: LA-ul apartine unei singure mobilitati
    @OneToOne
    @JoinColumn(name = "mobility_id", nullable = false) // cheie straina catre Mobility
    private Mobility mobility;
    
}