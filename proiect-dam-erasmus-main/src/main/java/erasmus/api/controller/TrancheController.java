package erasmus.api.controller;

import erasmus.api.dto.TrancheDTO;
import erasmus.commons.enums.Status;
import erasmus.domain.model.Tranche;
import erasmus.domain.repository.TrancheRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tranches")
public class TrancheController {

    private final TrancheRepository trancheRepository;

    public TrancheController(TrancheRepository trancheRepository) {
        this.trancheRepository = trancheRepository;
    }

    /**
     * UC: Student vede tranșele mobilității sale
     * (implementare minimă – fără SecurityContext)
     */
    @GetMapping("/my")
    public List<TrancheDTO> getMyTranches() {

        // TEMPORAR: returnăm TOATE tranșele
        // (arhitectural, filtrarea după student se face ulterior)
        return trancheRepository.findByScholarshipPlan_Mobility_Status(Status.APPROVED)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private TrancheDTO toDTO(Tranche tranche) {
        TrancheDTO dto = new TrancheDTO();
        dto.setId(tranche.getId());
        dto.setAmount(tranche.getAmount());
        dto.setStatus(tranche.getStatus());
        return dto;
    }
}

