package erasmus.service;

import erasmus.commons.enums.MobilityType;
import erasmus.domain.model.MobilityBudget;

// Importuri necesare pentru JUnit 5 (din pom.xml)
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Asta e o clasa de test simpla, nu are nevoie de @SpringBootTest
class MobilityServiceTest {

    // Asta e testul pt Regula 101
    @Test
    void testCalculBugetMobilitateScurta() {
        
        // 1. Arrange (Pregatirea)
        // Cream o instanta noua a serviciului, ca pe un obiect normal.
        // Nu avem nevoie de @Autowired pt ca testam doar metoda de calcul,
        // care nu depinde de alte servicii.
        MobilityService service = new MobilityService();
        
        int durata = 10; // 10 zile
        MobilityType tip = MobilityType.SHORT_DURATION;

        // 2. Act (Actiunea)
        // Chemi metoda pe care vrei sa o testezi
        MobilityBudget buget = service.calculateMobilityBudget(durata, tip);

        // 3. Assert (Verificarea)
        // Verificam daca rezultatul e cel asteptat (950.0)
        double expected = 950.0; // (70.0 * 10) + 250.0
        double actual = buget.getAmountEurTotal();
        
        assertEquals(expected, actual, "Calculul bugetului pt 10 zile scurte e gresit");
    }
    
    // TODO: Adauga un test similar pt mobilitate lunga (Regula 102)
    // @Test
    // void testCalculBugetMobilitateLunga() {
    //    ...
    // }
}