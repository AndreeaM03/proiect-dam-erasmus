package erasmus.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import erasmus.commons.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class MobilityDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("studentName")
    private String studentName;

    @JsonProperty("universityName")
    private String universityName;

    @JsonProperty("startDate")
    private LocalDate startDate;

    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("status")
    private Status status;
}
