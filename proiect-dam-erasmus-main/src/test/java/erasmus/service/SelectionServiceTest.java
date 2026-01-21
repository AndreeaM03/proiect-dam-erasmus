package erasmus.service;

import erasmus.domain.model.Application;
import erasmus.domain.model.Interview;
import erasmus.domain.repository.ApplicationRepository;
import erasmus.domain.repository.InterviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SelectionServiceTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    // TODO: Ponderile astea ar trebui puse in application.properties
    private static final double PONDERE_MEDIE = 0.40;
    private static final double PONDERE_INTERVIU = 0.60; // Simplificat

    /**
     * UC6: Calculeaza scorurile si genereaza clasamentul
     * E chemata de HR.
     */
    public List<Application> calculateAndRankCandidates() {
        
        // 1. Ia toate aplicatiile care asteapta scorul
        // List<Application> applications = applicationRepository.findByStatus(Status.APPLIED);
        // Simulare pana avem Enum:
        List<Application> applications = applicationRepository.findAll();


        // 2. Calculeaza scorul pt fiecare
        for (Application app : applications) {
            
            // Ia interviul asociat aplicatiei
            Interview interview = interviewRepository.findByApplicationId(app.getId())
                    .orElse(null); // Gaseste interviul

            if (interview != null) {
                // Presupunem ca 'score' din Application e media academica initial
                double academicScore = app.getScore(); 
                double interviewScore = 5.0; // Simulare: ia nota din interview.getEvaluationNotes()
                
                double finalScore = calculateFinalScore(academicScore, interviewScore);
                
                app.setScore(finalScore); // Seteaza scorul final
                // app.setStatus(Status.SCORED);
                applicationRepository.save(app);
            }
        }

        // 3. Genereaza clasamentul (sorteaza lista descrescator)
        applications.sort(Comparator.comparing(Application::getScore).reversed());

        return applications;
    }

    /**
     * Regula de calcul (UC6): aplica ponderile
     */
    private double calculateFinalScore(double academicScore, double interviewScore) {
        
        // Asta e implementarea regulii de business (ponderi)
        return (academicScore * PONDERE_MEDIE) + (interviewScore * PONDERE_INTERVIU);
    }
}