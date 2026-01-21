package erasmus.domain.model;

import erasmus.commons.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tranches")
public class TrancheTest {

    @Id
    private String id;

    private double amount; // value e cuvant cheie in sql
    private Status status;

    // Relatie n-1: mai multe transe apartin unui singur plan de burse
    @ManyToOne
    @JoinColumn(name = "scholarship_plan_id", nullable = false) // cheie straina catre ScholarshipPlan
    private ScholarshipPlan scholarshipPlan;

}