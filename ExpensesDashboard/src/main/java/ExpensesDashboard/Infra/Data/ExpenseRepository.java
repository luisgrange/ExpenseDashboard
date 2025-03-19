package ExpensesDashboard.Infra.Data;

import ExpensesDashboard.Domain.Entities.Expense;
import ExpensesDashboard.Domain.Enums.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
   List<Expense> findByType(ExpenseType type);
}
