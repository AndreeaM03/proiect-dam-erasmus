package org.example.proiectdamerasmusclient.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import erasmus.api.dto.StudentApplicationDTO;
import erasmus.api.dto.UniversityDTO;
import org.example.proiectdamerasmusclient.api.UniversityApiClient;
import org.example.proiectdamerasmusclient.api.ApplicationApiClient;

import java.time.temporal.ChronoUnit;

@Route("apply")
@PageTitle("Înscriere Erasmus")
public class StudentApplicationView extends VerticalLayout {

    private final ApplicationApiClient applicationApiClient;
    private final UniversityApiClient universityApiClient;

    public StudentApplicationView(
            ApplicationApiClient applicationApiClient,
            UniversityApiClient universityApiClient
    ) {
        this.applicationApiClient = applicationApiClient;
        this.universityApiClient = universityApiClient;

        H2 title = new H2("Înscriere student la mobilitate Erasmus");

        // ---------- DATE STUDENT ----------
        TextField firstName = new TextField("Prenume");
        TextField lastName = new TextField("Nume");
        TextField cnp = new TextField("CNP");

        // ---------- MOBILITATE ----------
        ComboBox<UniversityDTO> university = new ComboBox<>("Universitate");
        university.setItemLabelGenerator(UniversityDTO::getName);
        university.setItems(universityApiClient.getAllUniversities());

        // ---------- PERIOADĂ ----------
        DatePicker startDate = new DatePicker("Data început");
        DatePicker endDate = new DatePicker("Data final");

        // ---------- MOTIVAȚIE (UI only, momentan) ----------
        TextArea motivation = new TextArea("Scrisoare de motivație");
        motivation.setMinHeight("120px");

        // ---------- LAYOUT ----------
        FormLayout form = new FormLayout();
        form.add(
                firstName, lastName,
                cnp, university,
                startDate, endDate,
                motivation
        );
        form.setColspan(motivation, 2);

        // ---------- SUBMIT ----------
        Button submit = new Button("Trimite candidatura");
        submit.addClickListener(e -> {

            if (firstName.isEmpty() || lastName.isEmpty()
                    || cnp.isEmpty()
                    || university.isEmpty()
                    || startDate.isEmpty() || endDate.isEmpty()) {

                Notification.show("Completează toate câmpurile obligatorii");
                return;
            }

            StudentApplicationDTO dto = new StudentApplicationDTO();
            dto.setFirstName(firstName.getValue());
            dto.setLastName(lastName.getValue());
            dto.setCnp(cnp.getValue());
            dto.setUniversityId(university.getValue().getId());
            dto.setStartDate(startDate.getValue());
            dto.setEndDate(endDate.getValue());

            try {
                applicationApiClient.apply(dto);

                Notification.show(
                        "Candidatura a fost trimisă cu succes",
                        2500,
                        Notification.Position.MIDDLE
                );

                UI.getCurrent().navigate("student/mobilities");

            } catch (Exception ex) {
                Notification.show("Eroare la trimiterea candidaturii");
            }
        });

        setMaxWidth("900px");
        setWidthFull();
        add(title, form, submit);
    }
}
