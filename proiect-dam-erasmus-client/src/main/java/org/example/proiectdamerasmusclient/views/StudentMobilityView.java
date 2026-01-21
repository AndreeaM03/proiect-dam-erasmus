package org.example.proiectdamerasmusclient.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
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
import java.util.*;

@Route(value = "student/mobilities", layout = MainLayout.class)
@PageTitle("Student – Parcurs mobilitate")
public class StudentMobilityView extends VerticalLayout {

    private final MobilityApiClient api;

    private final Grid<MobilityDTO> grid = new Grid<>(MobilityDTO.class, false);
    private final TextField search = new TextField("Caută (universitate / status)");
    private final Div details = new Div();

    public StudentMobilityView(MobilityApiClient api) {
        this.api = api;

        setPadding(true);
        setSpacing(true);

        add(new H2("Student – Parcurs cereri de mobilitate"));

        List<MobilityDTO> data = api.getAllMobilities();

        ListDataProvider<MobilityDTO> provider =
                new ListDataProvider<>(data);

        configureGrid(provider);
        configureDetails();

        search.setWidth("420px");
        search.addValueChangeListener(e -> {
            String q = e.getValue() == null ? "" : e.getValue().toLowerCase();
            provider.setFilter(m -> {
                String uni = safe(m.getUniversityName()).toLowerCase();
                String st = m.getStatus() == null ? "" : m.getStatus().name().toLowerCase();
                return uni.contains(q) || st.contains(q);
            });
        });

        HorizontalLayout top = new HorizontalLayout(search);
        top.setAlignItems(Alignment.END);

        HorizontalLayout content = new HorizontalLayout(grid, details);
        content.setWidthFull();
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, details);

        add(top, content);
    }


    private void configureGrid(ListDataProvider<MobilityDTO> provider) {
        grid.addColumn(MobilityDTO::getUniversityName)
                .setHeader("Universitate")
                .setAutoWidth(true);

        grid.addColumn(m -> fmt(m.getStartDate()))
                .setHeader("Start")
                .setAutoWidth(true);

        grid.addColumn(m -> fmt(m.getEndDate()))
                .setHeader("End")
                .setAutoWidth(true);

        grid.addColumn(new ComponentRenderer<>(m -> new StatusBadge(m.getStatus()))
                )
                .setHeader("Status")
                .setAutoWidth(true);


        grid.setItems(provider);
        grid.setHeight("520px");

        grid.asSingleSelect()
                .addValueChangeListener(e -> showDetails(e.getValue()));
    }

    private void configureDetails() {
        details.addClassName("card");
        details.getStyle().set("min-height", "520px");
        details.add(new H3("Detalii"));
        details.add(new Paragraph("Selectează o mobilitate din tabel."));
    }

    private void showDetails(MobilityDTO m) {
        details.removeAll();
        details.add(new H3("Detalii cerere"));

        if (m == null) {
            details.add(new Paragraph("Selectează o mobilitate din tabel."));
            return;
        }

        details.add(line("Student", safe(m.getStudentName())));
        details.add(line("Universitate", safe(m.getUniversityName())));
        details.add(line("Perioadă",
                fmt(m.getStartDate()) + " → " + fmt(m.getEndDate())));
        details.add(line("Status curent",
                m.getStatus() == null ? "-" : m.getStatus().name()));

        details.add(new Hr());

        details.add(new H4("Parcurs cerere"));
        details.add(buildProgress(m.getStatus()));
    }

    private Component buildProgress(Status status) {
        List<Status> steps =
                List.of(Status.DRAFT, Status.APPLIED, Status.PROPOSED, Status.APPROVED);

        if (status == Status.REJECTED) {
            steps = List.of(Status.DRAFT, Status.APPLIED, Status.PROPOSED, Status.REJECTED);
        }

        OrderedList ol = new OrderedList();
        for (Status s : steps) {
            ListItem li = new ListItem(s.name());
            li.addClassName("step");

            if (status == s) li.addClassName("step-current");
            if (status != null && steps.indexOf(s) < steps.indexOf(status))
                li.addClassName("step-done");

            ol.add(li);
        }
        return ol;
    }

    private Component line(String key, String value) {
        return new Paragraph(key + ": " + value);
    }

    private String fmt(LocalDate d) {
        if (d == null) return "-";
        return d.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private String safe(String s) {
        return s == null ? "-" : s;
    }

    private <T> List<T> safeList(List<T> list) {
        return list == null ? new ArrayList<>() : list;
    }
}
