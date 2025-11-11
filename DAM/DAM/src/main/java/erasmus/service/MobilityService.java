package erasmus.service;

import erasmus.api.dto.ApplicationRequestDTO;

import erasmus.domain.model.Mobility;
import erasmus.domain.model.MobilityBudget;
import erasmus.domain.model.Student;
import erasmus.domain.model.University;

import erasmus.domain.repository.MobilityRepository;
import erasmus.domain.repository.StudentRepository;
import erasmus.domain.repository.UniversityRepository;

import erasmus.service.BudgetService;

import erasmus.commons.enums.MobilityType;
import erasmus.commons.enums.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobilityService {

    @Autowired
    private MobilityRepository mobilityRepository;
    
    @Autowired
    private BudgetService budgetService; // ca sa verifice daca tara are bani

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UniversityRepository universityRepository;
    
    // TODO: Constantele astea ar trebui puse in application.properties
    private static final double DAILY_RATE_SHORT = 70.0;
    private static final double MONTHLY_RATE_LONG = 500.0;
    private static final double TRANSPORT_COST = 250.0;

    /**
     * Creeaza o mobilitate noua (UC3.1)
     * Calculeaza bugetul si verifica daca tara isi permite.
     */
    public Mobility createMobility(ApplicationRequestDTO dto) {
        
        // Extrage datele din DTO folosind repository-urile
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Studentul nu exista"));
        
        University university = universityRepository.findById(dto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("Universitatea nu exista"));
        
        int duration = dto.getDuration();
        MobilityType type = dto.getMobilityType();
        
        // Calculeaza bugetul specific (Regula 101/102)
        MobilityBudget budget = calculateMobilityBudget(duration /*, type*/);
        
        // Verifica daca tara are destui bani (Regula 83/98)
        if (!budgetService.canAffordMobility(university.getCountry(), budget.getAmountEurTotal())) {
            throw new RuntimeException("Bugetul tarii e depasit!");
        }

        // Creeaza mobilitatea
        Mobility mobility = new Mobility();
        mobility.setStudent(student);
        mobility.setUniversity(university);
        mobility.setType(type);
        mobility.setStatus(Status.PROPOSED);
        mobility.setMobilityBudget(budget);
        budget.setMobility(mobility); // Seteaza legatura inversa

        return mobilityRepository.save(mobility);
    }

    /**
     * Regula 101/102: Calculeaza MobilityBudget (pe zi sau pe luna)
     */
    public MobilityBudget calculateMobilityBudget(int duration /*, MobilityType type*/) {
        
        double totalAmount = 0;
        
        // if (type == MobilityType.SHORT_DURATION) {
        //     totalAmount = TRANSPORT_COST + (DAILY_RATE_SHORT * duration);
        // } else if (type == MobilityType.LONG_DURATION) {
        //     totalAmount = TRANSPORT_COST + (MONTHLY_RATE_LONG * duration);
        // }
        
        // Simulare pana cand avem enum-ul
        totalAmount = TRANSPORT_COST + (DAILY_RATE_SHORT * duration);

        MobilityBudget budget = new MobilityBudget();
        budget.setAmountEurTotal(totalAmount);
        return budget;
    }

    /**
     * Aproba o mobilitate (parte din UC7)
     * E chemata de Coordonator.
     * Dupa aprobare, scade banii din bugetul tarii.
     */
    public Mobility approveMobility(String mobilityId, String approverUserId) {
        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow(() -> new RuntimeException("Mobilitatea nu exista"));
        
        // TODO: Verifica daca user-ul e Coordonator

        // mobility.setStatus(Status.APPROVED);
        // mobility.setApprover(userRepository.findById(approverUserId).get());
        
        // Acum, anunta BudgetService sa scada banii
        String country = mobility.getUniversity().getCountry();
        double amount = mobility.getMobilityBudget().getAmountEurTotal();
        
        budgetService.updateUsedBudget(country, amount); // Scade banii!

        return mobilityRepository.save(mobility);
    }
}