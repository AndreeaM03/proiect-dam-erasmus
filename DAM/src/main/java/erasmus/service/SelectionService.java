package erasmus.service;

import erasmus.domain.model.Application;
import erasmus.domain.model.Interview;
import erasmus.domain.repository.ApplicationRepository;
import erasmus.domain.repository.InterviewRepository;
import erasmus.commons.enums.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SelectionService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    // TODO: Ponderile astea ar trebui puse in application.properties
    private static final double PONDERE_MEDIE = 0.40;
    private static final double PONDERE_INTERVIU = 0.60; // Simplificat

    /**
     * calculeaza scorurile si genereaza clasamentul - HR
     */
    public List<Application> calculateAndRankCandidates() {
        
        // ia toate aplicatiile care asteapta scorul
        List<Application> applications = applicationRepository.findByStatus(Status.APPLIED);


        // clculeaza scorul pt fiecare
        for (Application app : applications) {
            
            // interviul asociat aplicatiei
            Interview interview = interviewRepository.findByApplicationId(app.getId())
                    .orElse(null);

            if (interview != null) {
                double academicScore = app.getScore();
                double interviewScore = 5.0; // Simulare: ia nota din interview.getEvaluationNotes()
                
                double finalScore = calculateFinalScore(academicScore, interviewScore);
                
                app.setScore(finalScore);
                // app.setStatus(Status.SCORED);
                applicationRepository.save(app);
            }
        }

        // genereaza clasamentul (sorteaza lista descrescator)
        applications.sort(Comparator.comparing(Application::getScore).reversed());

        return applications;
    }

    /**
     * aplica ponderile
     */
    private double calculateFinalScore(double academicScore, double interviewScore) {
        
        // implementarea regulii de business (ponderi)
        return (academicScore * PONDERE_MEDIE) + (interviewScore * PONDERE_INTERVIU);
    }
}