package org.example.proiectdamerasmusclient.views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.*;
import erasmus.api.dto.MobilityDTO;
import erasmus.commons.enums.Status;
import org.example.proiectdamerasmusclient.api.MobilityApiClient;
import org.example.proiectdamerasmusclient.components.StatusBadge;
import com.vaadin.flow.component.Component;

import java.util.*;
import java.util.stream.Collectors;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Dashboard")
public class DashboardView extends VerticalLayout {

    private final MobilityApiClient api;

    public DashboardView(MobilityApiClient api) {
        this.api = api;
        setPadding(true);
        setSpacing(true);

        add(new H2("Dashboard"));

        List<MobilityDTO> all = safeList(api.getAllMobilities());

        Map<Status, Long> counts = all.stream()
                .filter(m -> m.getStatus() != null)
                .collect(Collectors.groupingBy(MobilityDTO::getStatus, Collectors.counting()));

        add(statsRow(counts));

        // “Recent” – sortare simplă după startDate (dacă e null, la final)
        all.sort(
                Comparator.comparing(
                        MobilityDTO::getStartDate,
                        Comparator.nullsLast(Comparator.naturalOrder())
                ).reversed()
        );


        VerticalLayout recent = new VerticalLayout();
        recent.add(new H3("Cereri recente"));

        // tabel simplu (fără Grid ca să fie diferit)
        UnorderedList ul = new UnorderedList();
        for (MobilityDTO m : all.stream().limit(6).toList()) {
            ListItem li = new ListItem(
                    (m.getStudentName() == null ? "Student" : m.getStudentName())
                            + " → " +
                            (m.getUniversityName() == null ? "University" : m.getUniversityName())
            );
            li.add(new StatusBadge(m.getStatus()));
            ul.add(li);
        }
        recent.add(ul);

        add(recent);
    }

    private Component statsRow(Map<Status, Long> counts) {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();

        row.add(statCard("PROPOSED", counts.getOrDefault(Status.PROPOSED, 0L)));
        row.add(statCard("APPROVED", counts.getOrDefault(Status.APPROVED, 0L)));
        row.add(statCard("REJECTED", counts.getOrDefault(Status.REJECTED, 0L)));
        row.add(statCard("ACTIVE", counts.getOrDefault(Status.ACTIVE, 0L)));

        return row;
    }

    private Component statCard(String title, long value) {
        Div card = new Div();
        card.addClassName("card");

        Span t = new Span(title);
        t.addClassName("card-title");

        Span v = new Span(String.valueOf(value));
        v.addClassName("card-value");

        card.add(t, v);
        return card;
    }

    private <T> List<T> safeList(List<T> list) {
        return list == null ? new ArrayList<>() : list;
    }
}
