package erasmus.domain.model;

// Folosim Jakarta pt Spring Boot 3+ (JPA)
import jakarta.persistence.*;
import lombok.Data;

import erasmus.commons.enums.Status;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "projects")
public class Project {

    @Id
    private String id;

    // Atribute
    private String title;
    private Date startDate;
    private Date endDate;
    private Status status;
    private String description;

    // Relatia 1-n cu CountryBudget
    @OneToMany(mappedBy = "project")
    private Set<CountryBudget> countryBudgets;

}
