package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import erasmus.commons.enums.Status;
import erasmus.commons.enums.MobilityType;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "mobilities")
public class Mobility {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    private MobilityType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;

    @OneToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    // Relatie n-1: mai multe mobilitati apartin unui Student
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false) // cheie straina catre Student
    private Student student;

    // Relatie n-1: mobilitatea e la o singura universitate
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false) // cheie straina catre University
    private University university;

    // Relatie n-1: mobilitatea e aprobata de un singur User (Coordonator)
    @ManyToOne
    @JoinColumn(name = "approver_id") // cheie straina catre User (Coordonator)
    private User approver;

    // Relatie 1-1: mobilitatea are un singur buget
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mobility_budget_id", nullable = true)
    private MobilityBudget mobilityBudget;

    // Relatie 1-1: mobilitatea are un singur LA (Learning Agreement)
    @OneToOne(mappedBy = "mobility")
    private LearningAgreement learningAgreement;

    // Relatie 1-1: mobilitatea are un singur plan de burse
    @OneToOne(mappedBy = "mobility")
    private ScholarshipPlan scholarshipPlan;
}