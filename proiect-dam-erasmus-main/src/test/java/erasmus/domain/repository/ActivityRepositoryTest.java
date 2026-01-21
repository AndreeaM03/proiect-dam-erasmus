package erasmus.domain.repository;

import erasmus.domain.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepositoryTest extends JpaRepository<Activity, String> {

}