package erasmus.domain.repository;

import erasmus.domain.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // gaseste toate cheltuielile asociate unui anume buget de tara
    List<Expense> findByCountryBudgetId(Long budgetId);
    
}