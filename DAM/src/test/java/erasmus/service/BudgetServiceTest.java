package erasmus.service;

import erasmus.domain.model.CountryBudget;
import erasmus.domain.repository.CountryBudgetRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

    @Mock
    private CountryBudgetRepository countryBudgetRepository;

    @InjectMocks
    private BudgetService budgetService;

    @Test
    void testPoatePermiteMobilitatea() {

        CountryBudget buget = new CountryBudget();
        buget.setRemainingEur(1000.0); // 1000 euro ramasi
        double costMobilitate = 500.0; // 500 euro de cheltuit

        // Cand serviciul cere bugetul pt "Spania", da-i bugetul asta
        when(countryBudgetRepository.findByCountry("Spania"))
            .thenReturn(Optional.of(buget));

        // trb sa fie adevarat
        boolean rezultat = budgetService.canAffordMobility("Spania", costMobilitate);

        assertTrue(rezultat);
    }

    @Test
    void testNuPermiteMobilitatea() {
        CountryBudget buget = new CountryBudget();
        buget.setRemainingEur(1000.0); // 1000 euro ramasi
        double costMobilitate = 1500.0; // 1500 euro de cheltuit

        when(countryBudgetRepository.findByCountry("Spania"))
            .thenReturn(Optional.of(buget));

        boolean rezultat = budgetService.canAffordMobility("Spania", costMobilitate);

        // trb sa fie fals
        assertFalse(rezultat);
    }
}