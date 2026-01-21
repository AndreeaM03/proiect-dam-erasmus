package erasmus.domain.repository;

import erasmus.domain.model.Tranche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrancheRepository extends JpaRepository<Tranche, String> {

    // gaseste toate transele asociate unui plan de bursa
    List<Tranche> findByScholarshipPlanId(String planId);
    
}