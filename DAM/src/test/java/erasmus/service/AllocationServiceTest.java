package erasmus.service;

import erasmus.domain.model.Application;
import erasmus.domain.model.Mobility;
import erasmus.domain.repository.ApplicationRepository;
import erasmus.domain.repository.MobilityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AllocationServiceTest {

    @Autowired
    private ApplicationRepository applicationRepository; // ca sa gaseasca aplicatia selectata

    @Autowired
    private MobilityRepository mobilityRepository; // ca sa gaseasca mobilitatea

    @Autowired
    private BudgetService budgetService; // ca sa verifice bugetul tarii

    @Autowired
    private MobilityService mobilityService; // ca sa execute aprobarea

    /**
     * UC7: Aprobarea finala a alocarii de catre Coordonator
     * Verifica bugetul tarii (Regula 83/98) inainte de a aproba.
     */
    @Transactional // Asigura ca totul (verificare + aprobare) se intampla corect
    public Mobility approveAllocation(String applicationId, String mobilityId, String coordinatorUserId) {
        
        // 1. Gaseste aplicatia si mobilitatea
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Aplicatia nu exista"));
        
        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow(() -> new RuntimeException("Mobilitatea nu exista"));

        // 2. Ia datele necesare pentru verificare
        String country = mobility.getUniversity().getCountry();
        double amountToSpend = mobility.getMobilityBudget().getAmountEurTotal();

        // 3. Asta e REGULA CRITICA 83/98: Verifica daca tara isi permite
        if (!budgetService.canAffordMobility(country, amountToSpend)) {
            // Daca nu, respinge
            // app.setStatus(Status.REJECTED);
            // mobility.setStatus(Status.REJECTED);
            applicationRepository.save(app);
            mobilityRepository.save(mobility);
            
            throw new RuntimeException("Aprobare esuata: Bugetul tarii (" + country + ") e depasit.");
        }

        // 4. Daca bugetul e OK, deleaga aprobarea
        // MobilityService va seta statusul "Approved"
        // SI va chema BudgetService.updateUsedBudget() pentru a scadea banii
        return mobilityService.approveMobility(mobilityId, coordinatorUserId);
    }
}