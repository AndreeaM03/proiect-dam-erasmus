package erasmus.api.dto;

import erasmus.commons.enums.MobilityType;
import lombok.Data;

@Data
public class ApplicationRequestDTOTest {

    // Astea sunt datele pe care le trimite frontend-ul
    
    private String studentId;
    private String universityId;
    
    private MobilityType mobilityType;
    private int duration;
}