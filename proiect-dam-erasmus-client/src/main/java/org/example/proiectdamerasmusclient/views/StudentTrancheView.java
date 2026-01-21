package org.example.proiectdamerasmusclient.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.provider.ListDataProvider;

import erasmus.api.dto.TrancheDTO;
import org.example.proiectdamerasmusclient.api.TrancheApiClient;

import java.util.List;

@Route(value = "student/tranchee", layout = MainLayout.class)
@PageTitle("Transe bursă")
public class StudentTrancheView extends VerticalLayout {

    private final TrancheApiClient api;
    private final Grid<TrancheDTO> grid = new Grid<>(TrancheDTO.class, false);

    public StudentTrancheView(TrancheApiClient api) {
        this.api = api;

        setPadding(true);
        setSpacing(true);

        add(new H2("Transe bursă Erasmus"));

        configureGrid();
        loadData();
    }

    private void configureGrid() {

        grid.addColumn(TrancheDTO::getId)
                .setHeader("ID")
                .setAutoWidth(true);

        grid.addColumn(TrancheDTO::getAmount)
                .setHeader("Sumă (€)")
                .setAutoWidth(true);

        grid.addColumn(t -> t.getStatus().name())
                .setHeader("Status")
                .setAutoWidth(true);

        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        add(grid);
    }

    private void loadData() {
        List<TrancheDTO> tranches = api.getMyTranches();

        System.out.println("TRANSE DIN BACKEND = " + tranches); // DEBUG

        if (tranches == null || tranches.isEmpty()) {
            add(new Paragraph("Nu există tranșe disponibile."));
            return;
        }
        grid.setItems(new ListDataProvider<>(tranches));
        grid.getDataProvider().refreshAll();
    }
}
