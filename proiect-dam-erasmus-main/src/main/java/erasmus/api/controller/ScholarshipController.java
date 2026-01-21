package erasmus.api.controller;

import erasmus.api.dto.ScholarshipDTO;
import erasmus.domain.model.Tranche;
import erasmus.domain.repository.ScholarshipPlanRepository;
import erasmus.service.ScholarshipPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scholarships")
public class ScholarshipController {

    private final ScholarshipPlanRepository scholarshipPlanRepository;

    public ScholarshipController(ScholarshipPlanRepository repo) {
        this.scholarshipPlanRepository = repo;
    }

    @GetMapping("/overview")
    public List<ScholarshipDTO> getOverview() {

        return scholarshipPlanRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        sp -> sp.getMobility().getUniversity()
                ))
                .entrySet()
                .stream()
                .map(entry -> {

                    var university = entry.getKey();
                    var plans = entry.getValue();

                    double totalBudget = plans.stream()
                            .mapToDouble(p ->
                                    p.getMobility()
                                            .getMobilityBudget()
                                            .getAmountEurTotal()
                            )
                            .sum();

                    double usedBudget = plans.stream()
                            .flatMap(p -> p.getTranches().stream())
                            .mapToDouble(Tranche::getAmount)
                            .sum();

                    ScholarshipDTO dto = new ScholarshipDTO();
                    dto.setUniversityName(university.getName());
                    dto.setCountry(university.getCountry());
                    dto.setTotalBudget(totalBudget);
                    dto.setUsedBudget(usedBudget);
                    dto.setRemainingBudget(totalBudget - usedBudget);
                    dto.setNumberOfMobilities(plans.size());

                    return dto;
                })
                .toList();
    }
}

