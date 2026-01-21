package org.example.proiectdamerasmusclient.api;

import erasmus.api.dto.ApplicationRequestDTO;
import erasmus.api.dto.StudentApplicationDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApplicationApiClient {

    private final WebClient webClient;

    public ApplicationApiClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8081/api")
                .build();
    }

    public void apply(StudentApplicationDTO dto) {
        webClient.post()
                .uri("/applications")
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
