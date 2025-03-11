package ExpensesDashboard.Application.Services;

import ExpensesDashboard.Application.DTOs.ExpenseRequestDto;
import ExpensesDashboard.Application.DTOs.UpdateExpenseRequestDto;
import ExpensesDashboard.Domain.Enums.ExpenseType;
import ExpensesDashboard.Infra.Data.ExpenseRepository;
import ExpensesDashboard.Infra.Data.UserRepository;
import ExpensesDashboard.Domain.Entities.Expense;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseService {
    private final ExpenseRepository repository;
    private final UserRepository userRepository;


    public List<Expense> getExpenses(){
        List<Expense> expenses = repository.findAll();

        return expenses;
    }
    public boolean create(ExpenseRequestDto expenseToCreate){
        var user = userRepository.findByEmail(expenseToCreate.userEmail());
        if(user.isEmpty()){
            return false;
        }

        Expense newExpense = new Expense();
        newExpense.setName(expenseToCreate.name());
        newExpense.setAmount(expenseToCreate.amount());
        newExpense.setType(expenseToCreate.type());
        newExpense.setCreatedAt(LocalDateTime.now());
        newExpense.setUser(user.get());

        repository.save(newExpense);

        return true;
    }

    public Expense UpdateExpense(String id, UpdateExpenseRequestDto updatedExpense){
        return repository.findById(id)
                .map(expense -> {
                    expense.setName(updatedExpense.name());
                    expense.setType(updatedExpense.type());
                    expense.setAmount(updatedExpense.amount());
                    return repository.save(expense);
                }).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public List<Expense> getByType(String type){
        var expenseType = ExpenseType.valueOf(type);
        return repository.findByType(expenseType);
    }

    public boolean deleteExpense(String id){
        try {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return true;
            }

            return false;
        } catch (DataAccessException e) {
            return false;
        }
    }


}
