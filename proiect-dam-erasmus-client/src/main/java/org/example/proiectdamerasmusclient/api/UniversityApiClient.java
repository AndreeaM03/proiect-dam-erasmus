package org.example.proiectdamerasmusclient.api;

import erasmus.api.dto.UniversityDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class UniversityApiClient {

    private final WebClient webClient;

    public UniversityApiClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    public List<UniversityDTO> getAllUniversities() {
        return webClient.get()
                .uri("/api/universities")
                .retrieve()
                .bodyToFlux(UniversityDTO.class)
                .collectList()
                .block();
    }
}
