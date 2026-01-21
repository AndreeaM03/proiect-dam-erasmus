package org.example.proiectdamerasmusclient.api;

import erasmus.api.dto.ScholarshipDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class ScholarshipApiClient {

    private final WebClient webClient;

    public ScholarshipApiClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8081/api")
                .build();
    }

    /**
     * Overview burse (pentru coordonator).
     * GET /api/scholarships/overview
     */
    public List<ScholarshipDTO> getScholarshipsOverview() {
        try {
            return webClient.get()
                    .uri("/scholarships/overview")
                    .retrieve()
                    .bodyToFlux(ScholarshipDTO.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            // fallback sigur pentru UI (read-only)
            return List.of();
        }
    }
}

