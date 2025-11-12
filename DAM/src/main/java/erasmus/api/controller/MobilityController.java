package erasmus.api.controller;

import erasmus.domain.model.Mobility;
import erasmus.service.AllocationService;
import erasmus.service.MobilityService;
import erasmus.api.dto.ApplicationRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mobilities")
public class MobilityController {

    @Autowired
    private MobilityService mobilityService;

    @Autowired
    private AllocationService allocationService;

    /**
     * Studentul propune o mobilitate
     * Primeste datele prin HTTP POST.
     */
    @PostMapping
    public ResponseEntity<Mobility> createMobility(
        @RequestBody ApplicationRequestDTO dto
    ) {
        
        try {
            Mobility newMobility = mobilityService.createMobility(dto);
            return ResponseEntity.ok(newMobility);
            
        } catch (RuntimeException e) {
            // daca bugetul e depasit
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * Coordonatorul aproba alocarea
     */
    @PostMapping("/{mobilityId}/approve")
    public ResponseEntity<Mobility> approveMobility(
            @PathVariable String mobilityId,
            @RequestParam String applicationId,
            @RequestParam String coordinatorUserId
    ) {
        
        try {
            Mobility approvedMobility = allocationService.approveAllocation(
                applicationId,
                mobilityId,
                coordinatorUserId
            );
            return ResponseEntity.ok(approvedMobility);
            
        } catch (RuntimeException e) {
            // return ResponseEntity.badRequest().body(e.getMessage());
            return ResponseEntity.status(400).build(); // Simulare eroare
        }
    }
}