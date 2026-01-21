package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "universities")
public class UniversityTest {

    @Id
    private String id;

    private String country; // pentru a lega logic de CountryBudget
    private String name;
    private String iban;
    private String address;

    // Relatie n-1: Mai multe universitati apartin unui buget de tara
    @ManyToOne
    @JoinColumn(name = "country_budget_id") // Cheia străină către CountryBudget
    private CountryBudget countryBudget;

    // Relatie 1-n: o universitate are mai multe locuri
    @OneToMany(mappedBy = "university")
    private Set<Slot> slots;
    
    // Relatie 1-n: La o universitate au loc mai multe mobilitati
    @OneToMany(mappedBy = "university")
    private Set<Mobility> mobilities;

}