package erasmus.api.controller;

import erasmus.api.dto.MobilityDTO;
import erasmus.api.mapper.MobilityMapper;
import erasmus.domain.model.Mobility;
import erasmus.domain.model.Student;
import erasmus.domain.model.University;
import erasmus.domain.repository.MobilityRepository;
import erasmus.domain.repository.StudentRepository;
import erasmus.domain.repository.UniversityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mobilities-crud")
public class MobilityResourcesController {

    @Autowired
    private MobilityRepository mobilityRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping
    public List<MobilityDTO> getAllMobility() {
        return mobilityRepository.findAll().stream()
                .map(MobilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MobilityDTO getMobility(@PathVariable String id) {
        Mobility mobility = mobilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mobility not found"));
        return MobilityMapper.toDTO(mobility);
    }

    @PostMapping
    public MobilityDTO createMobility(@RequestBody MobilityDTO dto) {

        Student student = studentRepository.findById(dto.getStudentName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        University uni = universityRepository.findById(dto.getUniversityName())
                .orElseThrow(() -> new RuntimeException("University not found"));

        Mobility mobility = MobilityMapper.toEntity(dto, student, uni);
        mobilityRepository.save(mobility);

        return MobilityMapper.toDTO(mobility);
    }

    @PutMapping("/{id}")
    public MobilityDTO updateMobility(@PathVariable String id, @RequestBody MobilityDTO dto) {

        Mobility mobility = mobilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mobility not found"));

        Student student = studentRepository.findById(dto.getStudentName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        University uni = universityRepository.findById(dto.getUniversityName())
                .orElseThrow(() -> new RuntimeException("University not found"));

        mobility.setStudent(student);
        mobility.setUniversity(uni);
        mobility.setStartDate(dto.getStartDate());
        mobility.setEndDate(dto.getEndDate());

        mobilityRepository.save(mobility);

        return MobilityMapper.toDTO(mobility);
    }

    @DeleteMapping("/{id}")
    public String deleteMobility(@PathVariable String id) {
        mobilityRepository.deleteById(id);
        return "Mobility deleted";
    }
}
