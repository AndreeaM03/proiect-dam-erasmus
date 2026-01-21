package erasmus.domain.model;

import jakarta.persistence.*;
import lombok.Data; // Folosim pt constructori, getteri, setteri pe toate modelele

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "activities")
public class ActivityTest {

    @Id
    private String id;

    private String name;
    private Date startDate;
    private Date endDate;

    // Relatie 1-n: o activitate are mai multe alocari (assignments)
    @OneToMany(mappedBy = "activity")
    private Set<Assignment> assignments;

}