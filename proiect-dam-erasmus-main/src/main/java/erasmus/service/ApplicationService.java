package erasmus.service;

import erasmus.api.dto.StudentApplicationDTO;
import erasmus.commons.enums.Status;
import erasmus.domain.model.Application;
import erasmus.domain.model.Mobility;
import erasmus.domain.model.Student;
import erasmus.domain.model.University;
import erasmus.domain.repository.ApplicationRepository;
import erasmus.domain.repository.MobilityRepository;
import erasmus.domain.repository.StudentRepository;
import erasmus.domain.repository.UniversityRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private final StudentRepository studentRepository;
    private final UniversityRepository universityRepository;
    private final ApplicationRepository applicationRepository;
    private final MobilityRepository mobilityRepository;

    public ApplicationService(
            StudentRepository studentRepository,
            UniversityRepository universityRepository,
            ApplicationRepository applicationRepository,
            MobilityRepository mobilityRepository
    ) {
        this.studentRepository = studentRepository;
        this.universityRepository = universityRepository;
        this.applicationRepository = applicationRepository;
        this.mobilityRepository = mobilityRepository;
    }

    /**
     * UC: Student depune candidatura Erasmus
     * → se creează Application (ID instant)
     * → se creează Mobility asociată
     */
    @Transactional
    public Mobility apply(StudentApplicationDTO dto) {

        // 1️⃣ Student (find or create)
        Student student = studentRepository
                .findByCnp(dto.getCnp())
                .orElseGet(() -> {
                    Student s = new Student();
                    s.setCnp(dto.getCnp());
                    s.setFirstName(dto.getFirstName());
                    s.setLastName(dto.getLastName());
                    return studentRepository.save(s);
                });

        // 2️⃣ Universitate
        University uni = universityRepository
                .findById(dto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("Universitatea nu există"));

        Application app = new Application();
        app.setStudent(student);
        app.setStatus(Status.APPLIED);
        applicationRepository.save(app);

        Mobility mobility = new Mobility();
        mobility.setStudent(student);
        mobility.setApplication(app);

        mobility.setUniversity(uni);
        mobility.setStatus(Status.PROPOSED);

        // perioadă mobilitate
        mobility.setStartDate(dto.getStartDate());
        mobility.setEndDate(dto.getEndDate());

        return mobilityRepository.save(mobility);
    }
}
