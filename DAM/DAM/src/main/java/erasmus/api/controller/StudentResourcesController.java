package erasmus.api.controller;

import erasmus.api.dto.StudentDTO;
import erasmus.api.mapper.StudentMapper;
import erasmus.domain.model.Student;
import erasmus.domain.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentResourcesController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StudentDTO getStudent(@PathVariable String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return StudentMapper.toDTO(student);
    }

    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO dto) {
        Student student = StudentMapper.toEntity(dto);
        studentRepository.save(student);
        return StudentMapper.toDTO(student);
    }

    @PutMapping("/{id}")
    public StudentDTO updateStudent(@PathVariable String id, @RequestBody StudentDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setCnp(dto.getCnp());

        studentRepository.save(student);
        return StudentMapper.toDTO(student);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable String id) {
        studentRepository.deleteById(id);
        return "Student deleted";
    }
}
