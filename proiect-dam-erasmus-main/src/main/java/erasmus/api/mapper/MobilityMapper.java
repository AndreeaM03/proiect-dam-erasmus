package erasmus.api.mapper;

import erasmus.api.dto.MobilityDTO;
import erasmus.domain.model.Mobility;
import erasmus.domain.model.Student;
import erasmus.domain.model.University;

public class MobilityMapper {

    public static MobilityDTO toDTO(Mobility mobility) {
        if (mobility == null) return null;

        MobilityDTO dto = new MobilityDTO();

        dto.setId(mobility.getId());
        dto.setStartDate(mobility.getStartDate());
        dto.setEndDate(mobility.getEndDate());

        // University name
        if (mobility.getUniversity() != null) {
            dto.setUniversityName(mobility.getUniversity().getName());
        }

        // Student name
        if (mobility.getStudent() != null) {
            String first = mobility.getStudent().getFirstName();
            String last = mobility.getStudent().getLastName();

            dto.setStudentName(
                    (first != null ? first : "") +
                            (last != null ? " " + last : "")
            );
        }

        // Status
        dto.setStatus(mobility.getStatus());

        return dto;
    }

    public static Mobility toEntity(MobilityDTO dto, Student student, University uni) {
        if (dto == null) return null;

        Mobility mobility = new Mobility();

        mobility.setStartDate(dto.getStartDate());
        mobility.setEndDate(dto.getEndDate());

        mobility.setStudent(student);
        mobility.setUniversity(uni);
        mobility.setStatus(dto.getStatus());

        return mobility;
    }

}

