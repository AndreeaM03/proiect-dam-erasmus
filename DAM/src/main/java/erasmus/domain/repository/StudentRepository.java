package erasmus.domain.repository;

import erasmus.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    // ca sa gasim profilul studentului dupa ID-ul contului User
    Optional<Student> findByUserId(String userId);

}