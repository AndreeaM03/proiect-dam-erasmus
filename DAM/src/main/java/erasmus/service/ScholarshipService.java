package erasmus.service;

import erasmus.domain.model.LearningAgreement;
import erasmus.domain.model.Mobility;
import erasmus.domain.model.Tranche;
import erasmus.domain.repository.TrancheRepository;
import erasmus.domain.repository.LearningAgreementRepository;
import erasmus.domain.repository.MobilityRepository;
import erasmus.service.BudgetService;
import erasmus.commons.enums.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScholarshipService {

    @Autowired
    private TrancheRepository trancheRepository;

    @Autowired
    private BudgetService budgetService;

    /**
     * gestioneaza si plateste o transa
     */
    public Tranche payTranche(String trancheId) {
        
        Tranche tranche = trancheRepository.findById(trancheId)
                .orElseThrow(() -> new RuntimeException("Transa nu exista"));

        if (tranche.getStatus() == Status.PAID) {
            throw new RuntimeException("Transa e deja platita");
        }

        // gaseste parintii transei ca sa ajungi la mobilitate si LA
        Mobility mobility = tranche.getScholarshipPlan().getMobility();
        LearningAgreement la = mobility.getLearningAgreement();

        // 2. Asta e REGULA CRITICA 85/99
        boolean mobilityApproved = mobility.getStatus() == Status.APPROVED;
        boolean laApproved = la.getStatus() == Status.APPROVED;

        if (mobilityApproved && laApproved) {
            tranche.setStatus(Status.PAID);
            
            // anunta BudgetService sa scada banii
            String country = mobility.getUniversity().getCountry();
            double amount = tranche.getAmount();
            
            budgetService.updateUsedBudget(country, amount);
            
            return trancheRepository.save(tranche);
            
        } else {
            throw new RuntimeException("Plata respinsa: Mobilitatea sau LA nu sunt aprobate.");
        }
    }
}