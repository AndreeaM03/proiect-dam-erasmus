package erasmus.domain.repository;

import erasmus.domain.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepositoryTest extends JpaRepository<University, String> {

    // gaseste universitatea dupa nume
    Optional<University> findByName(String name);
    
}