package erasmus.api.mapper;

import erasmus.api.dto.ApplicationDTO;
import erasmus.domain.model.Application;
import erasmus.domain.model.Student;
import erasmus.domain.model.Interview;

public class ApplicationMapper {

    public static ApplicationDTO toDTO(Application app) {
        if (app == null) return null;

        ApplicationDTO dto = new ApplicationDTO();
        dto.setId(app.getId());
        dto.setScore(app.getScore());

        // relații → doar ID
        dto.setStudent(app.getStudent().getId() != null ? app.getStudent() : null);
        dto.setInterview(app.getInterview().getId() != null ? app.getInterview() : null);

        return dto;
    }

    public static Application toEntity(ApplicationDTO dto, Student student, Interview interview) {
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
