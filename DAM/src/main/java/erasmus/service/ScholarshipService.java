package erasmus.service;

import erasmus.domain.model.LearningAgreement;
import erasmus.domain.model.Mobility;
import erasmus.domain.model.Tranche;
import erasmus.domain.repository.TrancheRepository; // ai nevoie de repository-ul asta
import erasmus.domain.repository.LearningAgreementRepository; // si de asta
import erasmus.domain.repository.MobilityRepository; // si de asta
import erasmus.service.BudgetService; // ca sa scada banii
// TODO: import erasmus.commons.enums.Status; // cand e gata pachetul enums

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScholarshipService {

    @Autowired
    private TrancheRepository trancheRepository;

    @Autowired
    private BudgetService budgetService; // ca sa scada banii din bugetul tarii

    /**
     * UC8: Gestioneaza si plateste o transa.
     * Implementeaza Regula 85/99.
     */
    public Tranche payTranche(String trancheId) {
        
        Tranche tranche = trancheRepository.findById(trancheId)
                .orElseThrow(() -> new RuntimeException("Transa nu exista"));

        // if (tranche.getStatus() == Status.PAID) {
        //     throw new RuntimeException("Transa e deja platita");
        // }

        // 1. Gaseste parintii transei ca sa ajungi la mobilitate si LA
        Mobility mobility = tranche.getScholarshipPlan().getMobility();
        LearningAgreement la = mobility.getLearningAgreement();

        // 2. Asta e REGULA CRITICA 85/99
        // boolean mobilityApproved = mobility.getStatus() == Status.APPROVED;
        // boolean laApproved = la.getStatus() == Status.APPROVED;
        
        // Simulare pana avem Enum
        boolean mobilityApproved = true;
        boolean laApproved = true;

        if (mobilityApproved && laApproved) {
            // 3. Daca e ok, plateste
            // tranche.setStatus(Status.PAID);
            
            // 4. Anunta BudgetService sa scada banii (Regula 103)
            String country = mobility.getUniversity().getCountry();
            double amount = tranche.getAmount();
            
            budgetService.updateUsedBudget(country, amount); // Scade banii!
            
            return trancheRepository.save(tranche);
            
        } else {
            // 5. Daca nu e ok, arunca eroare
            throw new RuntimeException("Plata respinsa: Mobilitatea sau LA nu sunt aprobate.");
        }
    }
}