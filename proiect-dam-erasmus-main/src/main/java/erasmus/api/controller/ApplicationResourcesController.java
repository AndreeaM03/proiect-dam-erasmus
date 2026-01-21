package erasmus.api.controller;

import erasmus.api.dto.ApplicationDTO;
import erasmus.api.mapper.ApplicationMapper;
import erasmus.domain.model.Application;
import erasmus.domain.model.Student;
import erasmus.domain.model.Interview;
import erasmus.domain.repository.ApplicationRepository;
import erasmus.domain.repository.StudentRepository;
import erasmus.domain.repository.InterviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/api/applications")
public class ApplicationResourcesController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @GetMapping
    public List<ApplicationDTO> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(ApplicationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ApplicationDTO getApplication(@PathVariable String id) {
        return applicationRepository.findById(id)
                .map(ApplicationMapper::toDTO)
                .orElse(null); // sau ResponseEntity.notFound()
    }

    @PostMapping
    public ApplicationDTO createApplication(@RequestBody ApplicationDTO dto) {

        Student student = studentRepository.findById(dto.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Interview interview = null;
        if (dto.getInterview() != null) {
            interview = interviewRepository.findById(dto.getInterview().getId())
                    .orElse(null);
        }

        Application newApp = ApplicationMapper.toEntity(dto, student, interview);
        applicationRepository.save(newApp);

        return ApplicationMapper.toDTO(newApp);
    }

    @PutMapping("/{id}")
    public ApplicationDTO updateApplication(@PathVariable String id, @RequestBody ApplicationDTO dto) {

        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setScore(dto.getScore());

        applicationRepository.save(app);
        return ApplicationMapper.toDTO(app);
    }

    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable String id) {
        applicationRepository.deleteById(id);
        return "Application deleted";
    }
}
