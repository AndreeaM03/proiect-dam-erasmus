package erasmus.api.mapper;

import erasmus.api.dto.StudentDTOTest;
import erasmus.domain.model.Student;

public class StudentMapperTest {

    public static StudentDTOTest toDTO(Student student) {
        if (student == null) return null;

        StudentDTOTest dto = new StudentDTOTest();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setCnp(student.getCnp());

        return dto;
    }

    public static Student toEntity(StudentDTOTest dto) {
        if (dto == null) return null;

        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setCnp(dto.getCnp());

        return student;
    }
}
