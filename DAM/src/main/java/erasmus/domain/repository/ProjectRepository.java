package erasmus.domain.repository;

import erasmus.domain.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    // findById("string_id") merge de la sine de exemplu

}