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
    private BudgetService budgetService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UniversityRepository universityRepository;
    
    // TODO: Constantele astea ar trebui puse in application.properties
    private static final double DAILY_RATE_SHORT = 70.0;
    private static final double MONTHLY_RATE_LONG = 500.0;
    private static final double TRANSPORT_COST = 250.0;

    /**
     * creeaza o mobilitate noua, calculeaza bugetul si verifica daca tara isi permite
     */
    public Mobility createMobility(ApplicationRequestDTO dto) {
        
        // Extrage datele din DTO folosind repository-urile
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Studentul nu exista"));
        
        University university = universityRepository.findById(dto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("Universitatea nu exista"));
        
        int duration = dto.getDuration();
        MobilityType type = dto.getMobilityType();
        
        // Calculeaza bugetul
        MobilityBudget budget = calculateMobilityBudget(duration, type);
        
        // Verifica daca tara are destui bani
        if (!budgetService.canAffordMobility(university.getCountry(), budget.getAmountEurTotal())) {
            throw new RuntimeException("Bugetul tarii e depasit!");
        }

        // creeaza mobilitatea
        Mobility mobility = new Mobility();
        mobility.setStudent(student);
        mobility.setUniversity(university);
        mobility.setType(type);
        mobility.setStatus(Status.PROPOSED);
        mobility.setMobilityBudget(budget);
        budget.setMobility(mobility);

        return mobilityRepository.save(mobility);
    }

    /**
     * calculeaza MobilityBudget (pe zi sau pe luna)
     */
    public MobilityBudget calculateMobilityBudget(int duration, MobilityType type) {
        
        double totalAmount = 0;
        
        if (type == MobilityType.SHORT_DURATION) {
            totalAmount = TRANSPORT_COST + (DAILY_RATE_SHORT * duration);
        } else if (type == MobilityType.LONG_DURATION) {
            totalAmount = TRANSPORT_COST + (MONTHLY_RATE_LONG * duration);
        }

        MobilityBudget budget = new MobilityBudget();
        budget.setAmountEurTotal(totalAmount);
        return budget;
    }

    /**
     * aproba o mobilitate, dupa aprobare scade banii din bugetul tarii
     */
    public Mobility approveMobility(String mobilityId, String approverUserId) {
        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow(() -> new RuntimeException("Mobilitatea nu exista"));
        
        // TODO: DE VAZUT! Verifica daca user-ul e Coordonator

        // mobility.setStatus(Status.APPROVED);
        // mobility.setApprover(userRepository.findById(approverUserId).get());
        
        // se scad banii
        String country = mobility.getUniversity().getCountry();
        double amount = mobility.getMobilityBudget().getAmountEurTotal();
        
        budgetService.updateUsedBudget(country, amount);

        return mobilityRepository.save(mobility);
    }
}