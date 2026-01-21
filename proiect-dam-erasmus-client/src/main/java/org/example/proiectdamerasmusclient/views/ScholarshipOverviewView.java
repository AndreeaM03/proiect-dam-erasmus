package org.example.proiectdamerasmusclient.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import erasmus.api.dto.ScholarshipDTO;
import org.example.proiectdamerasmusclient.api.ScholarshipApiClient;

import java.util.Comparator;
import java.util.List;

@Route(value = "coordinator/scholarships", layout = MainLayout.class)
@PageTitle("Burse – Overview")
public class ScholarshipOverviewView extends VerticalLayout {

    public ScholarshipOverviewView(ScholarshipApiClient api) {

        setPadding(true);
        setSpacing(true);

        add(new H2("Situația burselor Erasmus (din baza de date)"));

        List<ScholarshipDTO> scholarships = api.getScholarshipsOverview();

        if (scholarships == null || scholarships.isEmpty()) {
            add(new Paragraph("Nu există date disponibile despre burse."));
            return;
        }

        // 🔹 sortare: cele mai critice sus (buget rămas mic)
        scholarships.sort(
                Comparator.comparing(ScholarshipDTO::getRemainingBudget)
        );

        Grid<ScholarshipDTO> grid = new Grid<>(ScholarshipDTO.class, false);

        grid.addColumn(ScholarshipDTO::getUniversityName)
                .setHeader("Universitate")
                .setAutoWidth(true);

        grid.addColumn(ScholarshipDTO::getCountry)
                .setHeader("Țară")
                .setAutoWidth(true);

        grid.addColumn(s -> String.format("%.2f €", s.getTotalBudget()))
                .setHeader("Buget total")
                .setAutoWidth(true);

        grid.addColumn(s -> String.format("%.2f €", s.getUsedBudget()))
                .setHeader("Buget utilizat (ACTIVI)")
                .setAutoWidth(true);

        grid.addColumn(s -> String.format("%.2f €", s.getRemainingBudget()))
                .setHeader("Buget rămas")
                .setAutoWidth(true);

        grid.addColumn(ScholarshipDTO::getNumberOfMobilities)
                .setHeader("Mobilități")
                .setAutoWidth(true);

        // 🔹 indicator vizual utilizare buget
        grid.addComponentColumn(s -> {
            double percent = s.getTotalBudget() > 0
                    ? (s.getUsedBudget() / s.getTotalBudget()) * 100
                    : 0;

            ProgressBar bar = new ProgressBar(0, 100, percent);
            bar.setWidth("140px");
            return bar;
        }).setHeader("Grad utilizare");

        // 🔹 status bugetar (decizie managerială)
        grid.addComponentColumn(s -> {
            double remaining = s.getRemainingBudget();
            double total = s.getTotalBudget();

            Span label = new Span();

            if (remaining < 0) {
                label.setText("DEPĂȘIT");
                label.getStyle().set("color", "red");
            } else if (remaining < total * 0.2) {
                label.setText("ATENȚIE");
                label.getStyle().set("color", "orange");
            } else {
                label.setText("OK");
                label.getStyle().set("color", "green");
            }

            return label;
        }).setHeader("Stare buget");

        grid.setItems(scholarships);
        grid.setWidthFull();

        add(grid);

        // 🔹 SUMAR GENERAL (jos)
        double total = scholarships.stream()
                .mapToDouble(ScholarshipDTO::getTotalBudget)
                .sum();

        double used = scholarships.stream()
                .mapToDouble(ScholarshipDTO::getUsedBudget)
                .sum();

        add(new Hr());
        add(new Paragraph(
                "Buget total Erasmus: " + String.format("%.2f €", total)
                        + " | Buget utilizat (studenți ACTIVI): "
                        + String.format("%.2f €", used)
        ));
    }
}
