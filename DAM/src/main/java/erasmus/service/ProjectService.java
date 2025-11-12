package erasmus.service;

import erasmus.domain.model.Project;
import erasmus.domain.model.CountryBudget;
import erasmus.domain.model.University;
import erasmus.domain.model.Activity;
import erasmus.domain.model.Assignment;
import erasmus.domain.repository.ProjectRepository;
import erasmus.domain.repository.CountryBudgetRepository;
import erasmus.domain.repository.UniversityRepository;
import erasmus.domain.repository.ActivityRepository;
import erasmus.domain.repository.AssignmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CountryBudgetRepository countryBudgetRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    /**
     * @Transactional asigura ca ori se salveaza tot, ori nimic.
     */
    @Transactional
    public Project createProject(Project project, List<CountryBudget> budgets, List<University> universities) {
        
        // salveaza Proiectul
        Project savedProject = projectRepository.save(project);

        // salveaza Bugetele si leaga-le de proiect
        for (CountryBudget budget : budgets) {
            if (budget.getCapTotalEur() <= 0) {
                throw new RuntimeException("Bugetul trebuie sa fie pozitiv");
            }
            budget.setProject(savedProject);
            countryBudgetRepository.save(budget);
        }

        // salveaza universitatile si leaga de bugetul tarii
        for (University uni : universities) {
            CountryBudget budget = countryBudgetRepository.findByCountry(uni.getCountry())
                    .orElseThrow(() -> new RuntimeException("Buget negasit pt tara " + uni.getCountry()));
            
            uni.setCountryBudget(budget);
            universityRepository.save(uni);
        }

        // valideaza si activeaza proiectul
        // savedProject.setStatus(Status.ACTIVE);
        return projectRepository.save(savedProject);
    }

    /**
     * creeaza activitate de proiect
     */
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    /**
     * aloca user la o activitate
     */
    public Assignment assignUserToActivity(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
}