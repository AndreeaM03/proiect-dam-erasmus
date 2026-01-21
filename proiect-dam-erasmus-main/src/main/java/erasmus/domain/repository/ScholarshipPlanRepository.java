package erasmus.domain.repository;

import erasmus.domain.model.ScholarshipPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScholarshipPlanRepository
        extends JpaRepository<ScholarshipPlan, String> {
}

