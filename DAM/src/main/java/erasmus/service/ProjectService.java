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
import org.springframework.transaction.annotation.Transactional; // important pt UC1

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CountryBudgetRepository countryBudgetRepository;

    @Autowired
    private UniversityRepository universityRepository; // ai nevoie de repository-ul asta

    @Autowired
    private ActivityRepository activityRepository; // si de asta

    @Autowired
    private AssignmentRepository assignmentRepository; // si de asta

    /**
     * UC1: Creaza tot proiectul (proiect, bugete, universitati)
     * @Transactional asigura ca ori se salveaza tot, ori nimic.
     */
    @Transactional
    public Project createProject(Project project, List<CountryBudget> budgets, List<University> universities) {
        
        // 1. Salveaza Proiectul
        Project savedProject = projectRepository.save(project);

        // 2. Salveaza Bugetele si leaga-le de proiect
        for (CountryBudget budget : budgets) {
            // Validare (Regula 95)
            if (budget.getCapTotalEur() <= 0) {
                throw new RuntimeException("Bugetul trebuie sa fie pozitiv");
            }
            budget.setProject(savedProject);
            countryBudgetRepository.save(budget);
        }

        // 3. Salveaza Universitatile si leaga-le de bugetul tarii
        for (University uni : universities) {
            // Gaseste bugetul tarii (ex: "Spania")
            CountryBudget budget = countryBudgetRepository.findByCountry(uni.getCountry())
                    .orElseThrow(() -> new RuntimeException("Buget negasit pt tara " + uni.getCountry()));
            
            uni.setCountryBudget(budget);
            universityRepository.save(uni);
        }

        // 4. Valideaza si activeaza proiectul (Regula 25)
        // savedProject.setStatus(Status.ACTIVE);
        return projectRepository.save(savedProject);
    }

    /**
     * UC2: Creaza o activitate de proiect
     */
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    /**
     * UC2: Aloca un user la o activitate
     */
    public Assignment assignUserToActivity(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
}