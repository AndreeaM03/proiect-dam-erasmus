package erasmus.domain.repository;

import erasmus.domain.model.CountryBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryBudgetRepository extends JpaRepository<CountryBudget, Long> {

    // gaseste bugetul dupa string-ul country
    // Optional - previne erori (NullPointerException) daca nu gaseste nicio tara cu numele ala.
    Optional<CountryBudget> findByCountry(String country);
    
}