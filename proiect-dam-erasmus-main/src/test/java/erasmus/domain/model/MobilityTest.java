package erasmus.domain.model;

import erasmus.commons.enums.MobilityType;
import erasmus.commons.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "mobilities")
public class MobilityTest {

    @Id
    private String id;

    private MobilityType type;
    private Date startDate;
    private Date endDate;
    private Status status;



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
    @OneToOne(mappedBy = "mobility")
    private MobilityBudget mobilityBudget;

    // Relatie 1-1: mobilitatea are un singur LA (Learning Agreement)
    @OneToOne(mappedBy = "mobility")
    private LearningAgreement learningAgreement;

    // Relatie 1-1: mobilitatea are un singur plan de burse
    @OneToOne(mappedBy = "mobility")
    private ScholarshipPlan scholarshipPlan;

}