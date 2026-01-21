package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "interviews")
public class Interview {

    @Id
    private String id; //

    private Date date;
    private String evaluationNotes;

    // Relatie 1-1: interviul apartine unei singure aplicatii
    @OneToOne
    @JoinColumn(name = "application_id", nullable = false) // cheie straina catre Application
    private Application application;

    // Relatie n-1: mai multe interviuri sunt conduse de un User (HR)
    @ManyToOne
    @JoinColumn(name = "interviewer_id") // cheie straina catre User (cel care intervieveaza)
    private User interviewer;
    
}