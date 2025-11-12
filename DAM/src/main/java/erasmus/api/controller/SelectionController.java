package erasmus.api.controller;

import erasmus.domain.model.Application;
import erasmus.domain.model.Interview;
import erasmus.service.SelectionService;
// TODO: import erasmus.api.dto.InterviewDTO; // cand e gata DTO

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/selection")
public class SelectionController {

    @Autowired
    private SelectionService selectionService;
    
    // TODO: o sa fie nev de InterviewService aici

    /**
     * HR genereaza clasamentul
     * Primeste o cerere GET (sau POST daca trebuie sa initieze calculul).
     */
    @GetMapping("/rankings")
    public ResponseEntity<List<Application>> getRankings() {
        
        // sortare si calculare
        List<Application> rankedList = selectionService.calculateAndRankCandidates();
        
        return ResponseEntity.ok(rankedList);
    }

    /**
     * HR inregistreaza un interviu.
     */
    @PostMapping("/interviews")
    public ResponseEntity<Interview> createInterview(/*@RequestBody InterviewDTO dto*/) {
        
        // TODO: DTO care contine applicationId, date, evaluationNotes
        
        // Interview newInterview = interviewService.createInterview(dto);
        
        // return ResponseEntity.ok(newInterview);
        
        return ResponseEntity.ok(new Interview()); // Simulare
    }
}