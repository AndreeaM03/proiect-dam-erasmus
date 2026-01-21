package erasmus.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import erasmus.domain.model.Interview;
import erasmus.domain.model.Student;
import lombok.Data;

@Data
public class ApplicationDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("student")
    private Student Student;

    @JsonProperty("score")
    private Double score;

    @JsonProperty("interview")
    private Interview Interview;
}
