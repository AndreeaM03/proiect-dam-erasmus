package org.example.proiectdamerasmusclient.api;

import erasmus.api.dto.StudentDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
@Component
public class StudentApiClient {
    private final WebClient client = WebClient.create("http://localhost:8080");

    public List<StudentDTO> getStudents() {
        return client.get()
                .uri("/students")
                .retrieve()
                .bodyToFlux(StudentDTO.class)
                .collectList()
                .block();
    }
}