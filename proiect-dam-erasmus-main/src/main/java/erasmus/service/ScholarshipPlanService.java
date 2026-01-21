package erasmus.service;

import erasmus.api.dto.ScholarshipDTO;
import erasmus.commons.enums.Status;
import erasmus.domain.model.Mobility;
import erasmus.domain.model.ScholarshipPlan;
import erasmus.domain.model.Tranche;
import erasmus.domain.model.University;
import erasmus.domain.repository.ScholarshipPlanRepository;
import erasmus.domain.repository.TrancheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScholarshipPlanService {

    @Autowired
    private ScholarshipPlanRepository scholarshipPlanRepository;

    @Autowired
    private TrancheRepository trancheRepository;

    /**
     * COMMAND: creeaza plan + transe
     */
    @Transactional
    public ScholarshipPlan createForMobility(Mobility mobility) {
        ScholarshipPlan plan = new ScholarshipPlan();
        plan.setMobility(mobility);
        plan.setNrTranches(2);

        scholarshipPlanRepository.save(plan);

        Tranche t1 = new Tranche();
        t1.setScholarshipPlan(plan);
        t1.setAmount(1000.0);
        t1.setStatus(Status.PROPOSED);

        Tranche t2 = new Tranche();
        t2.setScholarshipPlan(plan);
        t2.setAmount(1000.0);
        t2.setStatus(Status.PROPOSED);

        trancheRepository.saveAll(List.of(t1, t2));


        return plan;
    }

    /**
     * QUERY: overview burse (READ-ONLY)
     */
    @Transactional(readOnly = true)
    public List<ScholarshipDTO> getScholarshipsOverview() {

        return scholarshipPlanRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        sp -> sp.getMobility().getUniversity()
                ))
                .entrySet()
                .stream()
                .map(entry -> {

                    University u = entry.getKey();
                    List<ScholarshipPlan> plans = entry.getValue();

                    double total = plans.stream()
                            .mapToDouble(p -> p.getMobility()
                                    .getMobilityBudget()
                                    .getAmountEurTotal())
                            .sum();

                    double used = plans.stream()
                            .flatMap(p -> p.getTranches().stream())
                            .mapToDouble(Tranche::getAmount)
                            .sum();

                    ScholarshipDTO dto = new ScholarshipDTO();
                    dto.setUniversityName(u.getName());
                    dto.setCountry(u.getCountry());
                    dto.setTotalBudget(total);
                    dto.setUsedBudget(used);
                    dto.setRemainingBudget(total - used);
                    dto.setNumberOfMobilities(plans.size());

                    return dto;
                })
                .toList();
    }

}

