package erasmus.api.controller;

import erasmus.domain.model.Project;
import erasmus.service.ProjectService;
// TODO: import erasmus.api.dto.ProjectCreationDTO; // cand faci DTO-urile

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // anunta Spring ca asta e un controller REST
@RequestMapping("/api/projects") // toate endopint-urile de aici incep cu /api/projects
public class ProjectController {

    @Autowired
    private ProjectService projectService; // injecteaza serviciul

    /**
     * Endpoint pentru UC1: Crearea unui proiect.
     * Primeste datele prin HTTP POST.
     */
    @PostMapping
    public ResponseEntity<Project> createProject(/*@RequestBody ProjectCreationDTO dto*/) {
        
        // TODO: Aici logica e complexa. Trebuie sa primesti un DTO
        // care contine Proiectul, lista de Bugete si lista de Universitati
        
        // Simulare simpla pana cand avem DTO:
        Project projectData = new Project(); // Asta ar veni din DTO
        // List<CountryBudget> budgets = dto.getBudgets();
        // List<University> universities = dto.getUniversities();
        
        // Project newProject = projectService.createProject(projectData, budgets, universities);
        
        // return ResponseEntity.ok(newProject);
        
        // Simulare simpla:
        return ResponseEntity.ok(new Project()); // returneaza un proiect gol deocamdata
    }
    
    /**
     * Endpoint pentru a gasi un proiect dupa ID.
     */
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable String projectId) {
        // Project project = projectRepository.findById(projectId); // Ar trebui sa fie in service
        // return ResponseEntity.ok(project);
        
        return ResponseEntity.ok(new Project()); // Simulare
    }
    
    // TODO: de adaugat endpoint-uri si pentru UC2 (createActivity, assignUser)
}