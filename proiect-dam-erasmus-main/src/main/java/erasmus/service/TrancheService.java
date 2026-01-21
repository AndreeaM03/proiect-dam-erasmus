package erasmus.service;

import erasmus.api.dto.TrancheDTO;
import erasmus.api.mapper.TrancheMapper;
import erasmus.commons.enums.Status;
import erasmus.domain.repository.TrancheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrancheService {

    @Autowired
    private TrancheRepository trancheRepository;

    public List<TrancheDTO> getTranchesForApprovedMobilities() {
        return trancheRepository
                .findByScholarshipPlan_Mobility_Status(Status.APPROVED)
                .stream()
                .map(TrancheMapper::toDTO)
                .toList();
    }
}
