package erasmus.domain.repository;

import erasmus.commons.enums.Status;
import erasmus.domain.model.Application;
// import erasmus.commons.enums.Status; // TODO cand e gata pachetul enums
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    // SelectionService o sa foloseasca asta ca sa ia toti candidatii
    //List<Application> findByStatus(Status status);
    
    // sau complex daca selectia se face pe proiect
    //List<Application> findByProjectIdAndStatus(String projectId, Status status);
}