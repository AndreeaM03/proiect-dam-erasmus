package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "country_budgets")
public class CountryBudgetTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributele
    private String country; // business key
    private double capTotalEur;
    private double remainingEur;

    // Relatia n-1 - mai multe bugete apartin unui proiect
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false) // cheie straina catre Project
    private Project project;

    // Relatia 1-n - un buget de tara acopera mai multe universitati
    @OneToMany(mappedBy = "countryBudget")
    private Set<University> universities;

}
