package erasmus.domain.repository;

import erasmus.domain.model.Application;
import erasmus.commons.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    // toti candidatii
    List<Application> findByStatus(Status status);
    
    // selectie pe proiect
    // List<Application> findByProjectIdAndStatus(String projectId, Status status);
}