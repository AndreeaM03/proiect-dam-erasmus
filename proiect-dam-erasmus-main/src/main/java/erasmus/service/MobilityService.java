package erasmus.service;

import erasmus.api.dto.ApplicationRequestDTO;

import erasmus.api.dto.MobilityDTO;
import erasmus.api.mapper.MobilityMapper;
import erasmus.domain.model.*;

import erasmus.domain.repository.*;

import erasmus.service.BudgetService;

import erasmus.commons.enums.MobilityType;
import erasmus.commons.enums.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MobilityService {

    @Autowired
    private MobilityRepository mobilityRepository;
    
    @Autowired
    private BudgetService budgetService;// ca sa verifice daca tara are bani

    @Autowired
    private ScholarshipPlanRepository scholarshipPlanRepository;

    @Autowired
    private TrancheRepository trancheRepository;
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
    public List<Mobility> findAll() {
        return mobilityRepository.findAll();
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
    public List<MobilityDTO> findByStudentDTO(String studentId) {
        return mobilityRepository.findByStudentId(studentId)
                .stream()
                .map(MobilityMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<MobilityDTO> findAllDTO() {
        return mobilityRepository.findAll()
                .stream()
                .map(MobilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Mobility updateStatus(String mobilityId, Status newStatus) {

        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow(() -> new RuntimeException("Mobilitatea nu exista"));

        mobility.setStatus(newStatus);

        if (newStatus == Status.APPROVED) {

            MobilityBudget budget = mobility.getMobilityBudget();

            if (budget == null) {

                // folosim durata din ApplicationRequest / Mobility
                // dacă nu o ai încă pe Mobility, pune un default
                int duration = 30; // fallback sigur (ex: 30 zile)

                budget = calculateMobilityBudget(duration);
                mobility.setMobilityBudget(budget);
                budget.setMobility(mobility);
            }

            String country = mobility.getUniversity().getCountry();
            double amount = budget.getAmountEurTotal();

            budgetService.updateUsedBudget(country, amount);
        }

        return mobilityRepository.save(mobility);
    }




    /**
     * Aproba o mobilitate (parte din UC7)
     * E chemata de Coordonator.
     * Dupa aprobare, scade banii din bugetul tarii.
     */
    @Transactional
    public Mobility approveMobility(String mobilityId, String coordinatorUserId) {

        Mobility mobility = mobilityRepository.findById(mobilityId)
                .orElseThrow();

        // 1️⃣ aprobi mobilitatea
        mobility.setStatus(Status.APPROVED);

        // 2️⃣ creezi scholarship planul
        ScholarshipPlan plan = new ScholarshipPlan();
        plan.setMobility(mobility);
        plan.setNrTranches(2);

        scholarshipPlanRepository.save(plan);   // OBLIGATORIU

        // 3️⃣ creezi tranșe
        Tranche t1 = new Tranche();
        t1.setScholarshipPlan(plan);
        t1.setAmount(1000);
        t1.setStatus(Status.APPROVED);

        Tranche t2 = new Tranche();
        t2.setScholarshipPlan(plan);
        t2.setAmount(1000);
        t2.setStatus(Status.APPROVED);

        trancheRepository.saveAll(List.of(t1, t2));

        // 4️⃣ salvezi mobilitatea
        return mobilityRepository.save(mobility);
    }



}