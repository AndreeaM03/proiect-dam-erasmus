package erasmus.service;

import erasmus.domain.model.CountryBudget;
import erasmus.domain.model.Expense;
import erasmus.domain.model.Mobility;
import erasmus.domain.repository.CountryBudgetRepository;
import erasmus.domain.repository.ExpenseRepository; // ai nevoie si de asta
import erasmus.domain.repository.MobilityRepository; // si de asta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private CountryBudgetRepository countryBudgetRepository;
    
    @Autowired
    private MobilityRepository mobilityRepository; // ca sa luam mobilitatile aprobate

    @Autowired
    private ExpenseRepository expenseRepository; // ca sa luam cheltuielile
    
    // TODO: ai nevoie si de TrancheRepository

    /**
     * Regula 103: Calculeaza si actualizeaza bugetul ramas pentru o tara.
     * remainingEur = capTotalEur − suma(mobilitati aprobate + cheltuieli + transe platite)
     */
    public void calculateRemainingBudget(String country) {
        
        CountryBudget budget = countryBudgetRepository.findByCountry(country)
                .orElseThrow(() -> new RuntimeException("Bugetul nu a fost gasit"));

        // 1. Ia toate mobilitatile aprobate pt tara asta
        // Asta e complicat, trebuie sa gasesti mobilitatile dupa tara
        // (prin University) care au status "Approved"
        // List<Mobility> mobilities = mobilityRepository.findApprovedByCountry(country);
        
        // 2. Ia toate cheltuielile
        // List<Expense> expenses = expenseRepository.findByCountryBudget(budget);
        
        // 3. Ia toate transele platite
        // ...

        double totalConsumed = 0;
        // ... iteram si adunam totalConsumed ...

        double remaining = budget.getCapTotalEur() - totalConsumed;
        budget.setRemainingEur(remaining);
        
        countryBudgetRepository.save(budget);
    }

    /**
     * Regula 83/98: Verifica daca o noua cheltuiala/mobilitate incape in buget.
     * Folosit de AllocationService inainte de aprobarea finala (UC7).
     */
    public boolean canAffordMobility(String country, double amountToSpend) {
        CountryBudget budget = countryBudgetRepository.findByCountry(country)
                .orElseThrow(() -> new RuntimeException("Bugetul nu a fost gasit"));

        return budget.getRemainingEur() >= amountToSpend;
    }

    /**
     * Metoda chemata de alte servicii (MobilityService, ScholarshipService)
     * cand o cheltuiala noua e aprobata, ca sa scada din buget.
     */
    public void updateUsedBudget(String country, double amountSpent) {
        CountryBudget budget = countryBudgetRepository.findByCountry(country)
                .orElseThrow(() -> new RuntimeException("Bugetul nu a fost gasit"));
        
        if (budget.getRemainingEur() < amountSpent) {
            throw new RuntimeException("Depasire buget!"); // Arunca o eroare custom
        }

        budget.setRemainingEur(budget.getRemainingEur() - amountSpent);
        countryBudgetRepository.save(budget);
    }
}