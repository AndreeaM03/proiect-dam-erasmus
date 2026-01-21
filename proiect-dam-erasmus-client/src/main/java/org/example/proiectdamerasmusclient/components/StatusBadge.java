package org.example.proiectdamerasmusclient.components;


import com.vaadin.flow.component.html.Span;
import erasmus.commons.enums.Status;

public class StatusBadge extends Span {

    public StatusBadge(Status status) {
        setText(status == null ? "-" : status.name());
        addClassName("status-badge");
        addClassName("status-" + status.name().toLowerCase());
    }
}
