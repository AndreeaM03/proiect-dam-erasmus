package erasmus.api.mapper;

import erasmus.api.dto.ApplicationDTOTest;
import erasmus.domain.model.Application;
import erasmus.domain.model.Interview;
import erasmus.domain.model.Student;

public class ApplicationMapperTest {

    public static ApplicationDTOTest toDTO(Application app) {
        if (app == null) return null;

        ApplicationDTOTest dto = new ApplicationDTOTest();
        dto.setId(app.getId());
        dto.setScore(app.getScore());

        // relații → doar ID
        dto.setStudent(app.getStudent().getId() != null ? app.getStudent() : null);
        dto.setInterview(app.getInterview().getId() != null ? app.getInterview() : null);

        return dto;
    }

    public static Application toEntity(ApplicationDTOTest dto, Student student, Interview interview) {
        if (dto == null) return null;

        Application app = new Application();
        app.setId(dto.getId());
        app.setScore(dto.getScore());

        // reconstruim relațiile prin agregate
        app.setStudent(student);
        app.setInterview(interview);

        return app;
    }
}
