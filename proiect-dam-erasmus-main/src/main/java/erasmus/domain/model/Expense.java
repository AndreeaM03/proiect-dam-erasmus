package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // atributele din uml
    private double amountEur;
    private String description;

    // Relatie n-1: mai multe cheltuieli apartin unui singur buget de tara
    @ManyToOne
    @JoinColumn(name = "country_budget_id", nullable = false) // cheie straina catre CountryBudget
    private CountryBudget countryBudget;
    
}