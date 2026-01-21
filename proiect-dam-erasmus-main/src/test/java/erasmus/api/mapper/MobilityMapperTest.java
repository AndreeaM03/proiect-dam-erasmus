package erasmus.api.mapper;

import erasmus.api.dto.MobilityDTOTest;
import erasmus.domain.model.Mobility;
import erasmus.domain.model.Student;
import erasmus.domain.model.University;

public class MobilityMapperTest {

    public static MobilityDTOTest toDTO(Mobility mobility) {
        if (mobility == null) return null;

        MobilityDTOTest dto = new MobilityDTOTest();
        dto.setId(mobility.getId());
        dto.setStartDate(mobility.getStartDate());
        dto.setEndDate(mobility.getEndDate());

        dto.setUniversity(
                mobility.getUniversity().getId() != null ? mobility.getUniversity() : null
        );

        return dto;
    }

    public static Mobility toEntity(MobilityDTOTest dto, Student student, University uni) {
        if (dto == null) return null;

        Mobility mobility = new Mobility();
        mobility.setId(dto.getId());
        mobility.setStartDate(dto.getStartDate());
        mobility.setEndDate(dto.getEndDate());

        mobility.setUniversity(uni);

        return mobility;
    }
}
