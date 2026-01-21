package erasmus.domain.repository;

import erasmus.commons.enums.Status;
import erasmus.domain.model.Mobility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobilityRepository extends JpaRepository<Mobility, String> {
    
    // Spring Data JPA intelege din numele metodelor gen:

    // toate mobilitatile unui student
    List<Mobility> findByStudentId(String studentId);
    
    // toate mobilitatile cu un anumit status
    List<Mobility> findByStatus(Status status);
}