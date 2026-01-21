package erasmus.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentApplicationDTO {
        private String cnp;
        private String firstName;
        private String lastName;
        private String universityId;
        private LocalDate startDate;
        private LocalDate endDate;
}

