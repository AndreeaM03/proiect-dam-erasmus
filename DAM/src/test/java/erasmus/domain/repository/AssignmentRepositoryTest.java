package erasmus.domain.repository;

import erasmus.domain.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepositoryTest extends JpaRepository<Assignment, String> {

    // Gaseste toate alocarile pentru o anume activitate
    List<Assignment> findByActivityId(String activityId);
    
    // Gaseste toate alocarile unui anume user
    List<Assignment> findByUserId(String userId);

}