package org.example.proiectdamerasmusclient.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.dependency.CssImport;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {

        // ===== HEADER =====
        H1 title = new H1("Erasmus – Frontend Client");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-xl)")
                .set("margin", "0");

        HorizontalLayout header = new HorizontalLayout(title);
        header.setWidthFull();
        header.setPadding(true);

        addToNavbar(header);

        // ===== MENU =====
        VerticalLayout menu = new VerticalLayout();
        menu.setPadding(true);
        menu.setSpacing(true);

        // --- GENERAL ---
        menu.add(new RouterLink("Dashboard", DashboardView.class));

        // --- STUDENT ---
        menu.add(new H1("Student"));
        menu.add(new RouterLink("Aplicare Mobilitate", StudentApplicationView.class));
        menu.add(new RouterLink("Parcurs mobilități", StudentMobilityView.class));
        menu.add(new RouterLink("Transe bursă", StudentTrancheView.class));

        // --- COORDONATOR ---
        menu.add(new H1("Coordonator"));
        menu.add(new RouterLink("Mobilități studenți", CoordinatorMobilityView.class));
        menu.add(new RouterLink("Overview burse", ScholarshipOverviewView.class));

        addToDrawer(menu);
    }
}
