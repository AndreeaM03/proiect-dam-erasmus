package erasmus.domain.model;

import erasmus.commons.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users") // "user" e cuvant rezervat in SQL, mai bine "users"
public class UserTest {

    @Id
    private String id; // id-ul din uml

    private String username;
    private Role role;

    // Relatie 1-1: un User (daca e student) are un profil de Student
    @OneToOne(mappedBy = "user")
    private Student student;

    // Relatie 1-n: un User (daca e HR) conduce mai multe interviuri
    // "interviewer" e campul din clasa Interview
    @OneToMany(mappedBy = "interviewer")
    private Set<Interview> conductedInterviews;

    // Relatie 1-n: un User (Coordonator) aproba mai multe mobilitati
    // "approver" e campul din clasa Mobility
    @OneToMany(mappedBy = "approver")
    private Set<Mobility> approvedMobilities;
    
    // Relatie 1-n: un User e alocat la mai multe task-uri (Assignment)
    @OneToMany(mappedBy = "user")
    private Set<Assignment> assignments;

}