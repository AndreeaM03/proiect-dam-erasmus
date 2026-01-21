package erasmus.api.controller;

import erasmus.api.dto.ProjectCreationDTO;
import erasmus.domain.model.Project;
import erasmus.service.ProjectService;
// TODO: import erasmus.api.dto.ProjectCreationDTO; // cand e gata DTO-urile

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * Crearea unui proiect
     */
    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestBody ProjectCreationDTO dto
    ) {
        try {
            Project newProject = projectService.createProject(
                    dto.getProjectDetails(),
                    dto.getBudgets(),
                    dto.getUniversities());
            return ResponseEntity.ok(newProject);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).build(); // Eroare
        }
    }

    /**
     * gaseste proiect dupa ID.
     */
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable String projectId) {
        // Project project = projectRepository.findById(projectId); // Ar trebui sa fie in service
        // return ResponseEntity.ok(project);

        return ResponseEntity.ok(new Project()); // simulare
    }

    // TODO: de adaugat endpoint-uri si pt createActivity, assignUser
}