package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "applications")
public class ApplicationTest {

    @Id
    private String id;

    // private Status status;
    private double score; // scorul calculat de SelectionService

    // Relatie n-1: mai multe aplicatii apartin unui Student
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false) // cheie straina catre Student
    private Student student;

    // Relatie 1-1: o aplicatie e evaluata printr-un Interviu
    @OneToOne(mappedBy = "application")
    private Interview interview;

}