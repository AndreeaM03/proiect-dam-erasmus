package erasmus.api.controller;

import erasmus.api.dto.MobilityDTO;
import erasmus.api.dto.ApplicationRequestDTO;
import erasmus.api.dto.StatusUpdateDTO;
import erasmus.api.mapper.MobilityMapper;
import erasmus.domain.model.Mobility;
import erasmus.service.AllocationService;
import erasmus.service.MobilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mobilities")
public class MobilityController {

    @Autowired
    private MobilityService mobilityService;

    @Autowired
    private AllocationService allocationService;

    @GetMapping
    public ResponseEntity<List<MobilityDTO>> getAllMobilities() {
        return ResponseEntity.ok(mobilityService.findAllDTO());
    }

    @PutMapping("/{mobilityId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String mobilityId,
            @RequestBody StatusUpdateDTO dto
    ) {
        mobilityService.updateStatus(mobilityId, dto.status());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<MobilityDTO>> getMobilitiesForStudent(
            @PathVariable String studentId
    ) {
        return ResponseEntity.ok(
                mobilityService.findByStudentDTO(studentId)
        );
    }

    @PostMapping
    public ResponseEntity<MobilityDTO> createMobility(
            @RequestBody ApplicationRequestDTO dto
    ) {
        Mobility mobility = mobilityService.createMobility(dto);
        return ResponseEntity.ok(MobilityMapper.toDTO(mobility));
    }

    @PostMapping("/{mobilityId}/approve")
    public ResponseEntity<MobilityDTO> approveMobility(
            @PathVariable String mobilityId
    ) {
        Mobility approved = mobilityService.approveMobility(mobilityId, "DUMMY");
        return ResponseEntity.ok(MobilityMapper.toDTO(approved));
    }
}
