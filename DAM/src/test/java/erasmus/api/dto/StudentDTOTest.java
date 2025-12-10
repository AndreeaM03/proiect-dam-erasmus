package erasmus.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StudentDTOTest {

    @JsonProperty("id")
    private String Id;

    @JsonProperty("firstname")
    private String FirstName;

    @JsonProperty("lastname")
    private String LastName;

    @JsonProperty("cnp")
    private String Cnp;
}