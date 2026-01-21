package erasmus.domain.repository;

import erasmus.domain.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewRepositoryTest extends JpaRepository<Interview, String> {

    // SelectionService cauta interviul pe baza aplicatiei
    Optional<Interview> findByApplicationId(String applicationId);

}