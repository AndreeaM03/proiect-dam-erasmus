package erasmus.api.dto;

import erasmus.domain.model.Project;
import erasmus.domain.model.CountryBudget;
import erasmus.domain.model.University;
import lombok.Data;
import java.util.List;

@Data
public class ProjectCreationDTO {

    private Project projectDetails;

    private List<CountryBudget> budgets;

    private List<University> universities;
}