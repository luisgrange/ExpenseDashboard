package ExpensesDashboard.Application.Services;

import ExpensesDashboard.Application.DTOs.ExpenseRequestDto;
import ExpensesDashboard.Application.DTOs.UpdateExpenseRequestDto;
import ExpensesDashboard.Infra.Data.ExpenseRepository;
import ExpensesDashboard.Infra.Data.UserRepository;
import ExpensesDashboard.Domain.Entities.Expense;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExpenseService {
    @Autowired
    private final ExpenseRepository repository;
    @Autowired
    private final UserRepository userRepository;

    public boolean create(ExpenseRequestDto expenseToCreate){
        var user = userRepository.findByEmail(expenseToCreate.userEmail());
        if(user.isEmpty()){
            return false;
        }

        Expense newExpense = new Expense();
        newExpense.setName(expenseToCreate.name());
        newExpense.setAmount(expenseToCreate.amount());
        newExpense.setType(expenseToCreate.type());
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


}
