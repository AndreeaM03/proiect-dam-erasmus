package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mobility_budgets")
public class MobilityBudgetTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amountEurTotal; // calculat de MobilityService

    // Relatie 1-1: bugetul apartine unei singure mobilitati
    @OneToOne
    @JoinColumn(name = "mobility_id", nullable = false) // cheie straina catre Mobility
    private Mobility mobility;

}