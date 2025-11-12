package erasmus.api.controller;

import erasmus.domain.model.Application;
import erasmus.domain.model.Interview;
import erasmus.service.SelectionService;
// TODO: import erasmus.api.dto.InterviewDTO; // cand faci DTO-urile

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // anunta Spring ca asta e un controller REST
@RequestMapping("/api/selection") // toate endpoint-urile incep cu /api/selection
public class SelectionController {

    @Autowired
    private SelectionService selectionService;
    
    // TODO: ai nevoie de InterviewService aici

    /**
     * Endpoint pentru UC6: HR genereaza clasamentul
     * Primeste o cerere GET (sau POST daca trebuie sa initieze calculul).
     */
    @GetMapping("/rankings")
    public ResponseEntity<List<Application>> getRankings() {
        
        // Cheama serviciul sa calculeze si sa sorteze
        List<Application> rankedList = selectionService.calculateAndRankCandidates();
        
        return ResponseEntity.ok(rankedList);
    }

    /**
     * Endpoint pentru UC3: HR inregistreaza un interviu
     * Primeste datele prin HTTP POST.
     */
    @PostMapping("/interviews")
    public ResponseEntity<Interview> createInterview(/*@RequestBody InterviewDTO dto*/) {
        
        // TODO: Aici logica e complexa. Trebuie sa primesti un DTO
        // care contine applicationId, date, evaluationNotes
        
        // Interview newInterview = interviewService.createInterview(dto);
        
        // return ResponseEntity.ok(newInterview);
        
        return ResponseEntity.ok(new Interview()); // Simulare
    }
}