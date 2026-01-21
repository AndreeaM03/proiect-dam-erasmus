package erasmus.api.mapper;

import erasmus.api.dto.TrancheDTO;
import erasmus.domain.model.Tranche;

public class TrancheMapper {
    public static TrancheDTO toDTO(Tranche t) {
        TrancheDTO dto = new TrancheDTO();
        dto.setId(t.getId());
        dto.setAmount(t.getAmount());
        dto.setStatus(t.getStatus());
        return dto;
    }
}
