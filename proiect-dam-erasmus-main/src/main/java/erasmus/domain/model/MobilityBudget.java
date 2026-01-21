package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "mobility_budgets")
public class MobilityBudget {

    @Id
    @GeneratedValue
    private Long id;

    private double amountEurTotal; // calculat de MobilityService

    // Relatie 1-1: bugetul apartine unei singure mobilitati
    @OneToOne(mappedBy = "mobilityBudget")
    private Mobility mobility;

}