package org.example.proiectdamerasmusclient.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import erasmus.api.dto.MobilityDTO;
import erasmus.commons.enums.Status;
import org.example.proiectdamerasmusclient.api.MobilityApiClient;
import org.example.proiectdamerasmusclient.components.StatusBadge;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Route(value = "coordinator/mobilities", layout = MainLayout.class)
@PageTitle("Coordonator – Status mobilități")
public class CoordinatorMobilityView extends VerticalLayout {

    private final MobilityApiClient api;

    private final Grid<MobilityDTO> grid = new Grid<>(MobilityDTO.class, false);
    private ListDataProvider<MobilityDTO> provider;

    private final TextField search = new TextField("Filtru text");
    private final ComboBox<Status> statusFilter = new ComboBox<>("Filtru status");

    private final ComboBox<Status> newStatus = new ComboBox<>("Status nou");
    private final Button apply = new Button("Aplică status");
    private final Button refresh = new Button("Refresh");

    public CoordinatorMobilityView(MobilityApiClient api) {
        this.api = api;

        setPadding(true);
        setSpacing(true);

        add(new H2("Coordonator – Gestionare cereri de mobilitate"));

        configureTopBar();
        configureGrid();
        reload();
    }

    private void configureTopBar() {
        search.setWidth("300px");

        statusFilter.setItems(Status.values());
        statusFilter.setClearButtonVisible(true);

        newStatus.setItems(Status.APPROVED, Status.REJECTED);
        newStatus.setClearButtonVisible(true);

        apply.addClickListener(e -> updateSelected());
        refresh.addClickListener(e -> reload());

        HorizontalLayout filters = new HorizontalLayout(search, statusFilter, refresh);
        HorizontalLayout actions = new HorizontalLayout(newStatus, apply);

        add(filters, actions);

        search.addValueChangeListener(e -> applyFilters());
        statusFilter.addValueChangeListener(e -> applyFilters());
    }

    private void configureGrid() {
        grid.addColumn(MobilityDTO::getStudentName)
                .setHeader("Student")
                .setAutoWidth(true);

        grid.addColumn(MobilityDTO::getUniversityName)
                .setHeader("Universitate")
                .setAutoWidth(true);

        grid.addColumn(m -> fmt(m.getStartDate()))
                .setHeader("Start")
                .setAutoWidth(true);

        grid.addColumn(m -> fmt(m.getEndDate()))
                .setHeader("End")
                .setAutoWidth(true);

        grid.addColumn(new ComponentRenderer<>(m -> new StatusBadge(m.getStatus())))
                .setHeader("Status")
                .setAutoWidth(true);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setHeight("520px");

        add(grid);
    }

    private void reload() {
        List<MobilityDTO> all = safeList(api.getAllMobilities());
        provider = new ListDataProvider<>(all);
        grid.setItems(provider);
        applyFilters();
    }

    private void applyFilters() {
        if (provider == null) return;

        String q = Optional.ofNullable(search.getValue()).orElse("").toLowerCase();
        Status st = statusFilter.getValue();

        provider.setFilter(m -> {
            boolean matchText =
                    q.isEmpty()
                            || safe(m.getStudentName()).toLowerCase().contains(q)
                            || safe(m.getUniversityName()).toLowerCase().contains(q);

            boolean matchStatus = (st == null) || m.getStatus() == st;

            return matchText && matchStatus;
        });
    }

    private void updateSelected() {
        MobilityDTO selected = grid.asSingleSelect().getValue();

        if (selected == null) {
            Notification.show("Selectează o cerere din tabel.");
            return;
        }

        if (newStatus.getValue() == null) {
            Notification.show("Selectează un status nou.");
            return;
        }

        try {
            if (newStatus.getValue() == Status.APPROVED) {
                api.approveMobility(selected.getId());
            } else {
                api.updateStatus(selected.getId(), newStatus.getValue());
            }

            Notification.show("Status actualizat.");
            reload();

        } catch (Exception ex) {
            Notification.show("Eroare la actualizare: " + ex.getMessage());
        }
    }

    private String fmt(LocalDate d) {
        if (d == null) return "-";
        return d.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private <T> List<T> safeList(List<T> list) {
        return list == null ? new ArrayList<>() : list;
    }
}
