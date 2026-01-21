package org.example.proiectdamerasmusclient.api;

import erasmus.api.dto.TrancheDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class TrancheApiClient {

    private final WebClient webClient;

    public TrancheApiClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8081/api")
                .build();
    }

    /**
     * Transele mobilității curente
     */
    public List<TrancheDTO> getMyTranches() {
        return webClient.get()
                .uri("/tranches/my")
                .retrieve()
                .bodyToFlux(TrancheDTO.class)
                .collectList()
                .block();
    }
}
