package org.example.proiectdamerasmusclient.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import erasmus.api.dto.StudentDTO;
import org.example.proiectdamerasmusclient.api.StudentApiClient;

@Route("students")
public class StudentsView extends VerticalLayout {

    private final StudentApiClient apiClient;

    public StudentsView(StudentApiClient apiClient) {
        this.apiClient = apiClient;

        Grid<StudentDTO> grid = new Grid<>(StudentDTO.class, false);
        grid.setItems(apiClient.getStudents());
        add(grid);
    }
}

