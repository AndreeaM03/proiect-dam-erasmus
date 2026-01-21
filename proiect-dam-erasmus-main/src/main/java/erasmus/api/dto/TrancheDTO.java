package erasmus.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import erasmus.commons.enums.Status;
import lombok.Data;

@Data
public class TrancheDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("status")
    private Status status;
}
