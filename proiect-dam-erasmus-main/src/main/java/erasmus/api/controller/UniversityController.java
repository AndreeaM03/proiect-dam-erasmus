package erasmus.api.controller;

import erasmus.api.dto.UniversityDTO;
import erasmus.domain.model.University;
import erasmus.domain.repository.UniversityRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityRepository repo;

    public UniversityController(UniversityRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<UniversityDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(u -> {
                    UniversityDTO dto = new UniversityDTO();
                    dto.setId(u.getId());
                    dto.setName(u.getName());
                    return dto;
                })
                .toList();
    }
}
