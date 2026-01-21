package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    // atributele din uml
    private String lastName;
    private String firstName;
    private String cnp;

    // Relatie 1-1: un student e legat de un singur cont user
    @OneToOne
    @JoinColumn(name = "user_id") // cheie straina catre User
    private User user;

    // Relatie 1-n: un student trimite mai multe aplicatii
    @OneToMany(mappedBy = "student")
    private Set<Application> applications;

    // Relatie 1-n: un student are mai multe mobilitati
    @OneToMany(mappedBy = "student")
    private Set<Mobility> mobilities;

}