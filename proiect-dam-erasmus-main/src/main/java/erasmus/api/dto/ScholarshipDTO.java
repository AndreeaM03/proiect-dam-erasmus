package erasmus.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScholarshipDTO {

    @JsonProperty("universityName")
    private String universityName;

    @JsonProperty("country")
    private String country;

    @JsonProperty("totalBudget")
    private double totalBudget;

    @JsonProperty("usedBudget")
    private double usedBudget;

    @JsonProperty("remainingBudget")
    private double remainingBudget;

    @JsonProperty("numberOfMobilities")
    private int numberOfMobilities;
}

