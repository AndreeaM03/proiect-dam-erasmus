package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Data
@Entity
@Table(name = "scholarship_plans")
public class ScholarshipPlan {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    private int nrTranches;

    // Relatie 1-1: planul apartine unei singure mobilitati
    @OneToOne
    @JoinColumn(name = "mobility_id", nullable = false) // cheie straina catre Mobility
    private Mobility mobility;

    // Relatie 1-n: un plan are mai multe transe
    @OneToMany(mappedBy = "scholarshipPlan")
    private Set<Tranche> tranches;

}