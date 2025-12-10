package erasmus.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import erasmus.domain.model.Student;
import erasmus.domain.model.University;
import lombok.Data;

import java.util.Date;

@Data
public class MobilityDTOTest {

    @JsonProperty("id")
    private String id;

    @JsonProperty("student")
    private Student student;

    @JsonProperty("university")
    private University university;

    @JsonProperty("startDate")
    private Date startDate;

    @JsonProperty("endDate")
    private Date endDate;
}
