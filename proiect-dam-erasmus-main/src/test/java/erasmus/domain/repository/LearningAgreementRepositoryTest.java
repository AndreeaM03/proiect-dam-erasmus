package erasmus.domain.repository;

import erasmus.domain.model.LearningAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearningAgreementRepositoryTest extends JpaRepository<LearningAgreement, String> {

    // ca LA-ul pe baza mobilitatii
    Optional<LearningAgreement> findByMobilityId(String mobilityId);
    
}