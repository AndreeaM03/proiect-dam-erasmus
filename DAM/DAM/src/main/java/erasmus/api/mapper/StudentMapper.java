package erasmus.api.mapper;

import erasmus.api.dto.StudentDTO;
import erasmus.domain.model.Student;

public class StudentMapper {

    public static StudentDTO toDTO(Student student) {
        if (student == null) return null;

        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setCnp(student.getCnp());

        return dto;
    }

    public static Student toEntity(StudentDTO dto) {
        if (dto == null) return null;

        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setCnp(dto.getCnp());

        return student;
    }
}
