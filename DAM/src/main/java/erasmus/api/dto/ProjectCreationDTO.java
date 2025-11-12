package erasmus.api.dto;

import erasmus.domain.model.Project;
import erasmus.domain.model.CountryBudget;
import erasmus.domain.model.University;
import lombok.Data;
import java.util.List;

@Data
public class ProjectCreationDTO {

    // Date pt entitatea Project
    private Project projectDetails;

    // Lista de bugete pe tara
    private List<CountryBudget> budgets;

    // Lista de universitati partenere
    private List<University> universities;
}