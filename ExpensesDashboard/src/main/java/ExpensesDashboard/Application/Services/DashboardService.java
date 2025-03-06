package ExpensesDashboard.Application.Services;

import ExpensesDashboard.Application.DTOs.LoggedUserResponseDto;
import ExpensesDashboard.Domain.Entities.Expense;
import ExpensesDashboard.Infra.Data.ExpenseRepository;
import ExpensesDashboard.Infra.Data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    public LoggedUserResponseDto getLoggedUserData(String userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var expenses = user.getExpenses();
        var totalPayed = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        var remainingAmount = user.getSalary() - totalPayed;

        return new LoggedUserResponseDto(user.getSalary(), totalPayed, remainingAmount);
    }
}
