package org.example.proiectdamerasmusclient.api;

import erasmus.api.dto.ApplicationRequestDTO;
import erasmus.api.dto.MobilityDTO;
import erasmus.api.dto.StatusUpdateDTO;
import erasmus.commons.enums.Status;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class MobilityApiClient {

    private final WebClient webClient;

    public MobilityApiClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8081/api")
                .build();
    }

    // ======================
    // COORDONATOR
    // ======================

    public List<MobilityDTO> getAllMobilities() {
        return webClient.get()
                .uri("/mobilities")
                .retrieve()
                .bodyToFlux(MobilityDTO.class)
                .collectList()
                .block();
    }

    public MobilityDTO getMobilityById(String id) {
        return webClient.get()
                .uri("/mobilities/{id}", id)
                .retrieve()
                .bodyToMono(MobilityDTO.class)
                .block();
    }

    // ======================
    // STUDENT
    // ======================

    public MobilityDTO createMobility(ApplicationRequestDTO dto) {
        return webClient.post()
                .uri("/mobilities")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(MobilityDTO.class)
                .block();
    }

    public List<MobilityDTO> getMyMobilities() {
        return webClient.get()
                .uri("/mobilities/my")
                .retrieve()
                .bodyToFlux(MobilityDTO.class)
                .collectList()
                .block();
    }
    public void updateStatus(String mobilityId, Status status) {
        webClient.put()
                .uri("/mobilities/{id}/status", mobilityId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new StatusUpdateDTO(status))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
    public void approveMobility(String mobilityId) {
        webClient.post()
                .uri("/mobilities/{id}/approve", mobilityId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
