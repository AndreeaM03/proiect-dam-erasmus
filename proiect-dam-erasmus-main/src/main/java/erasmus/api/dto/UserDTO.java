package erasmus.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import erasmus.commons.enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    @JsonProperty("id")
    private String Id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("role")
    private Role role;
}


