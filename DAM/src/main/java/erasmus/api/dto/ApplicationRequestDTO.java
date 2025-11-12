package erasmus.api.dto;

import erasmus.commons.enums.MobilityType;
import lombok.Data;

@Data
public class ApplicationRequestDTO {

    // Astea sunt datele pe care le trimite frontend-ul
    
    private String studentId; // ID-ul studentului care aplica
    private String universityId; // ID-ul universitatii la care aplica
    
    private MobilityType mobilityType; // SHORT_DURATION sau LONG_DURATION
    private int duration; // Numarul de zile (pt scurta) sau luni (pt lunga)
    
    // pot fi adaugate si alte campuri necesare pt formularul de aplicare
    // de ex: private double academicAverage;
}