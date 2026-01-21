package erasmus.api.controller;

import erasmus.api.dto.StudentApplicationDTO;
import erasmus.api.mapper.MobilityMapper;
import erasmus.api.dto.MobilityDTO;
import erasmus.domain.model.Mobility;
import erasmus.service.ApplicationService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public MobilityDTO apply(@RequestBody StudentApplicationDTO dto) {
        Mobility mobility = applicationService.apply(dto);
        return MobilityMapper.toDTO(mobility);
    }
}
